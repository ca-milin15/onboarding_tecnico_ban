package com.bancopichincha.credito.automotriz.solicitudcredito.application.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.bancopichincha.credito.automotriz.cliente.application.service.ClienteService;
import com.bancopichincha.credito.automotriz.ejecutivo.application.service.EjecutivoMediatorService;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ErrorTransaccionalDBRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;
import com.bancopichincha.credito.automotriz.solicitudcredito.application.application.dto.SolicitudCreditoDTO;
import com.bancopichincha.credito.automotriz.solicitudcredito.application.enumeration.EstadoSolicitudEnum;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCredito;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCreditoClienteXDiaXEstadoRuntimeException;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCreditoRepository;
import com.bancopichincha.credito.automotriz.vehiculo.application.service.VehiculoService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SolicitudCreditoServiceImpl implements SolicitudCreditoMediatorService {

	SolicitudCreditoRepository solicitudCreditoRepository;
	PropiedadesSistema propiedadesSistema;
	ClienteService clienteService;
	EjecutivoMediatorService ejecutivoMediatorService;
	VehiculoService vehiculoService;

	@Override
	public SolicitudCreditoDTO procesoGuardar(SolicitudCreditoDTO solicitudCreditoDTO) {
		validaciones(solicitudCreditoDTO);
		var solicitud = construirObjetoEntidadDesdeRequestCrear(solicitudCreditoDTO);
		solicitud = persistirEntidadesEnDB(solicitud);
		solicitudCreditoDTO.setIdSolicitudCredito(solicitud.getId());
		return solicitudCreditoDTO;
	}

	private void validaciones(SolicitudCreditoDTO solicitudCreditoDTO) {
		var diaActual = LocalDate.now();
		final LocalTime HORA_BUSQUEDA_INICIO_SOLICITUD_X_DIA = LocalTime.of(0, 0);
		final LocalTime HORA_BUSQUEDA_FIN_SOLICITUD_X_DIA = LocalTime.of(23, 59);

		validacionSolicitudesClienteXDia(solicitudCreditoDTO,
				LocalDateTime.of(diaActual, HORA_BUSQUEDA_INICIO_SOLICITUD_X_DIA),
				LocalDateTime.of(diaActual, HORA_BUSQUEDA_FIN_SOLICITUD_X_DIA));

		validacionVehiculoReserva(solicitudCreditoDTO);
	}

	private void validacionVehiculoReserva(SolicitudCreditoDTO solicitudCreditoDTO) {
		var listaSolicitudes = solicitudCreditoRepository.findByVehiculoIdAndEstado(solicitudCreditoDTO.getVehiculoId(),
				EstadoSolicitudEnum.REGISTRADA);
		if (!ObjectUtils.isEmpty(listaSolicitudes)) {
			var vehiculo = listaSolicitudes.stream().findFirst().get();
			throw new SolicitudCreditoClienteXDiaXEstadoRuntimeException(
					String.format(
							propiedadesSistema.getExcepciones().getNegocio().getSolicitudCredito()
									.getErrorVehiculoReservado(),
							solicitudCreditoDTO.getVehiculoId(), EstadoSolicitudEnum.REGISTRADA.getDescripcion(),
							vehiculo.getFecha()));
		}
	}

	private void validacionSolicitudesClienteXDia(SolicitudCreditoDTO solicitudCreditoDTO, LocalDateTime fechaInicio,
			LocalDateTime fechaFin) {
		var listaSolicitudes = solicitudCreditoRepository.findByClienteIdAndFechaBetweenAndEstado(
				solicitudCreditoDTO.getClienteId(), fechaInicio, fechaFin, EstadoSolicitudEnum.REGISTRADA);
		if (!ObjectUtils.isEmpty(listaSolicitudes)) {
			throw new SolicitudCreditoClienteXDiaXEstadoRuntimeException(
					String.format(
							propiedadesSistema.getExcepciones().getNegocio().getSolicitudCredito()
									.getErrorSolicitudesXDia(),
							solicitudCreditoDTO.getClienteId(), EstadoSolicitudEnum.REGISTRADA.getDescripcion(),
							LocalDate.now()));
		}
	}

	private SolicitudCredito persistirEntidadesEnDB(SolicitudCredito solicitudCredito) {
		try {
			return solicitudCreditoRepository.save(solicitudCredito);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(propiedadesSistema.getExcepciones().getNegocio()
					.getSolicitudCredito().getErrorIntegridadDatosAlmacenar(), e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	private SolicitudCredito construirObjetoEntidadDesdeRequestCrear(SolicitudCreditoDTO solicitudCreditoDTO) {
		var solicitudCredito = SolicitudCredito.builder();
		solicitudCredito.cliente(clienteService.procesoConsultarCliente(solicitudCreditoDTO.getClienteId()));
		solicitudCredito.ejecutivo(ejecutivoMediatorService.buscarEjecutivo(solicitudCreditoDTO.getEjecutivoId()));
		solicitudCredito.vehiculo(vehiculoService.buscarVehiculoPorId(solicitudCreditoDTO.getVehiculoId()));
		solicitudCredito.cuotas(solicitudCreditoDTO.getCuotas());
		solicitudCredito.mesesPlazo(solicitudCreditoDTO.getMesesPlazo());
		solicitudCredito.observacion(solicitudCreditoDTO.getObservacion());
		solicitudCredito.estado(EstadoSolicitudEnum.REGISTRADA);
		return solicitudCredito.build();
	}
}
