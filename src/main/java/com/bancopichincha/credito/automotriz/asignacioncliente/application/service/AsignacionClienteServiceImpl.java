package com.bancopichincha.credito.automotriz.asignacioncliente.application.service;

import java.math.BigInteger;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bancopichincha.credito.automotriz.asignacioncliente.application.dto.AsignacionClienteDTO;
import com.bancopichincha.credito.automotriz.asignacioncliente.domain.AsignacionCliente;
import com.bancopichincha.credito.automotriz.asignacioncliente.domain.AsignacionClienteRepository;
import com.bancopichincha.credito.automotriz.cliente.application.service.ClienteService;
import com.bancopichincha.credito.automotriz.patioautos.application.service.PatioAutosService;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ErrorTransaccionalDBRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AsignacionClienteServiceImpl implements AsignacionClienteService {

	AsignacionClienteRepository asignacionClienteRepository;
	PropiedadesSistema propiedadesSistema;
	ClienteService clienteService;
	PatioAutosService patioAutosService;

	@Override
	public AsignacionClienteDTO procesoGuardar(AsignacionClienteDTO asignacionClienteDTO) {
		var asignacionCliente = construirObjetoEntidadDesdeRequestCrear(asignacionClienteDTO);
		asignacionCliente = persistirEnDB(asignacionCliente);
		asignacionClienteDTO.setIdAsignacion(asignacionCliente.getId());
		return asignacionClienteDTO;
	}
	
	private AsignacionCliente construirObjetoEntidadDesdeRequestCrear(AsignacionClienteDTO asignacionClienteDTO) {
		var asignacion = AsignacionCliente.builder();
		if (!ObjectUtils.isEmpty(asignacionClienteDTO.getIdCliente())) {
			asignacion.cliente(clienteService.procesoConsultarCliente(asignacionClienteDTO.getIdCliente()));
		}

		if (!ObjectUtils.isEmpty(asignacionClienteDTO.getIdPatio())) {
			asignacion.patioAutos(patioAutosService.buscarXIdRetornaEntidad(asignacionClienteDTO.getIdPatio()));
		}
		return asignacion.build();
	}

	@Transactional
	private AsignacionCliente persistirEnDB(AsignacionCliente asignacionCliente) {
		try {
			return asignacionClienteRepository.save(asignacionCliente);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(propiedadesSistema.getExcepciones().getNegocio()
					.getAsignacionCliente().getErrorIntegridadDatosAlmacenar(), e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	
	@Override
	public AsignacionClienteDTO procesoActualizar(BigInteger id, AsignacionClienteDTO asignacionClienteDTO) {
		var asignacion = buscarPorIdEnDB(id);
		asignacion = construirObjetoEntidadDesdeRequestActualizar(asignacion, asignacionClienteDTO);
		asignacion = persistirEnDB(asignacion);
		return actualizarAsigClienteDTO(asignacion, asignacionClienteDTO);
	}

	private AsignacionClienteDTO actualizarAsigClienteDTO(AsignacionCliente asignacion,
			AsignacionClienteDTO asignacionClienteDTO) {
		var patio = asignacion.getPatioAutos();
		var cliente = asignacion.getCliente();
		asignacionClienteDTO.setIdPatio(patio.getId());
		asignacionClienteDTO.setNombrePatio(patio.getNombre());
		asignacionClienteDTO.setIdCliente(cliente.getId());
		asignacionClienteDTO.setNombreCliente(cliente.getNombres());
		return asignacionClienteDTO;
	}

	private AsignacionCliente construirObjetoEntidadDesdeRequestActualizar(AsignacionCliente asignacionCliente, AsignacionClienteDTO asignacionClienteDTO) {
		if (!ObjectUtils.isEmpty(asignacionClienteDTO.getIdCliente())) {
			asignacionCliente.setCliente(clienteService.procesoConsultarCliente(asignacionClienteDTO.getIdCliente()));
		}

		if (!ObjectUtils.isEmpty(asignacionClienteDTO.getIdPatio())) {
			asignacionCliente.setPatioAutos(patioAutosService.buscarXIdRetornaEntidad(asignacionClienteDTO.getIdPatio()));
		}
		return asignacionCliente;
	}
	
	private AsignacionCliente buscarPorIdEnDB(BigInteger id) {
		return asignacionClienteRepository.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String.format(propiedadesSistema
						.getExcepciones().getNegocio().getAsignacionCliente().getEntidadNoEncontrada(), id)));
	}

	@Override
	@Transactional
	public boolean procesoEliminar(BigInteger id) {
		var patio = buscarPorIdEnDB(id);
		try {
			asignacionClienteRepository.delete(patio);
			return true;
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(propiedadesSistema.getExcepciones().getNegocio()
					.getAsignacionCliente().getErrorIntegridadDatosAlmacenar());
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	@Override
	public AsignacionClienteDTO buscarXId(BigInteger id) {
		var asig = buscarPorIdEnDB(id);
		return actualizarAsigClienteDTO(asig, new AsignacionClienteDTO());
	}

}
