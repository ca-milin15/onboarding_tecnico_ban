package com.bancopichincha.credito.automotriz.vehiculo.application.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.bancopichincha.credito.automotriz.marca.application.MarcaMediatorService;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ErrorTransaccionalDBRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;
import com.bancopichincha.credito.automotriz.shared.infrastructure.Utilidades;
import com.bancopichincha.credito.automotriz.vehiculo.application.dto.VehiculoDTO;
import com.bancopichincha.credito.automotriz.vehiculo.application.enumerations.TipoVehiculoEnum;
import com.bancopichincha.credito.automotriz.vehiculo.domain.Vehiculo;
import com.bancopichincha.credito.automotriz.vehiculo.domain.VehiculoRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VehiculoServiceImpl implements VehiculoService {

	VehiculoRepository vehiculoRepository;
	MarcaMediatorService marcaMediatorService;
	PropiedadesSistema propiedadesSistema;

	@Transactional
	private Vehiculo persistirVehiculo(Vehiculo vehiculo) {
		try {
			return vehiculoRepository.save(vehiculo);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(
					propiedadesSistema.getExcepciones().getNegocio().getVehiculo().getErrorIntegridadDatosAlmacenar(),
					e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	@Override
	public VehiculoDTO guardarVehiculo(VehiculoDTO vehiculoRequest) {
		var vehiculo = construirObjetoVehiculoDesdeRequestCrear(vehiculoRequest);
		vehiculo = persistirVehiculo(vehiculo);
		return VehiculoDTO.builder().id(vehiculo.getId()).placa(vehiculo.getPlaca()).modelo(vehiculo.getModelo())
				.numeroChasis(vehiculo.getNumeroChasis())
				.tipo(!ObjectUtils.isEmpty(vehiculo.getTipo()) ? vehiculo.getTipo().getDescripcion() : "")
				.cilindraje(vehiculo.getCilindraje()).avaluo(vehiculo.getAvaluo())
				.nombreMarca(vehiculo.getMarca().getNombre()).build();
	}

	private Vehiculo construirObjetoVehiculoDesdeRequestCrear(VehiculoDTO vehiculoRequest) {
		var marca = marcaMediatorService.buscarMarcaPorId(vehiculoRequest.getIdMarca());

		var vehiculo = Vehiculo.builder().placa(vehiculoRequest.getPlaca()).modelo(vehiculoRequest.getModelo())
				.numeroChasis(vehiculoRequest.getNumeroChasis()).cilindraje(vehiculoRequest.getCilindraje())
				.avaluo(vehiculoRequest.getAvaluo()).marca(marca).build();

		if (!ObjectUtils.isEmpty(vehiculoRequest.getTipo())) {
			vehiculo.setTipo(TipoVehiculoEnum.getEnumByCode(vehiculoRequest.getTipo(),
					propiedadesSistema.getExcepciones().getGeneral().getEnumeradoNoEncontrado()));
		}

		return vehiculo;
	}

	public Vehiculo buscarVehiculoXIdRetornaEntidad(BigInteger id) {
		return buscarVehiculoPorIdEnDB(id);
	}

	private Vehiculo buscarVehiculoPorIdEnDB(BigInteger id) {
		return vehiculoRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String
				.format(propiedadesSistema.getExcepciones().getNegocio().getVehiculo().getEntidadNoEncontrada(), id)));
	}

	@Override
	public VehiculoDTO actualizarVehiculo(BigInteger id, VehiculoDTO vehiculoRequest) {
		var vehiculo = buscarVehiculoPorIdEnDB(id);
		var vehiculoActualizado = construirObjetoVehiculoDesdeRequestActualizar(vehiculo, vehiculoRequest);
		vehiculo = persistirVehiculo(vehiculoActualizado);
		return crearVehiculoDTODesdeEntidad(vehiculo);
	}

	private Vehiculo construirObjetoVehiculoDesdeRequestActualizar(Vehiculo vehiculo, VehiculoDTO vehiculoRequest) {
		var listaAttrNulos = Utilidades.obtenerAtributosconValorNull(vehiculoRequest);
		BeanUtils.copyProperties(vehiculoRequest, vehiculo, listaAttrNulos.toArray(new String[0]));

		if (!ObjectUtils.isEmpty(vehiculoRequest.getIdMarca())) {
			vehiculo.setMarca(marcaMediatorService.buscarMarcaPorId(vehiculoRequest.getIdMarca()));
		}

		if (!ObjectUtils.isEmpty(vehiculoRequest.getTipo())) {
			vehiculo.setTipo(TipoVehiculoEnum.getEnumByCode(vehiculoRequest.getTipo(),
					propiedadesSistema.getExcepciones().getGeneral().getEnumeradoNoEncontrado()));
		}

		return vehiculo;
	}

	@Override
	@Transactional
	public boolean eliminarVehiculo(BigInteger id) {
		var vehiculo = buscarVehiculoPorIdEnDB(id);
		try {
			vehiculoRepository.delete(vehiculo);
			return true;
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(
					propiedadesSistema.getExcepciones().getNegocio().getVehiculo().getErrorIntegridadDatosAlmacenar());
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	private static final String MES_DIA_HORA_INICIO = "01-01T00:00:00";
	private static final String MES_DIA_HORA_FIN = "12-31T23:59:59";

	@Override
	public List<VehiculoDTO> buscarVehiculoPorParams(Integer anio, BigInteger marcaId, Integer modelo) {
		LocalDateTime fechaInicio = null;
		LocalDateTime fechaFin = null;
		if (!ObjectUtils.isEmpty(anio)) {
			fechaInicio = LocalDateTime.parse(String.format("%s-%s", anio, MES_DIA_HORA_INICIO));
			fechaFin = LocalDateTime.parse(String.format("%s-%s", anio, MES_DIA_HORA_FIN));
		}
		var vehiculo = vehiculoRepository.buscarVehiculosPorParams(marcaId, modelo, fechaInicio, fechaFin);
		return crearListaVehiculoDTODesdeEntidad(vehiculo);
	}

	private List<VehiculoDTO> crearListaVehiculoDTODesdeEntidad(List<Vehiculo> vehiculos) {
		return vehiculos.stream().map(vehiculo -> {
			return crearVehiculoDTODesdeEntidad(vehiculo);
		}).collect(Collectors.toList());
	}

	private VehiculoDTO crearVehiculoDTODesdeEntidad(Vehiculo vehiculo) {
		return VehiculoDTO.builder().id(vehiculo.getId()).placa(vehiculo.getPlaca()).modelo(vehiculo.getModelo())
				.numeroChasis(vehiculo.getNumeroChasis())
				.tipo(!ObjectUtils.isEmpty(vehiculo.getTipo()) ? vehiculo.getTipo().getDescripcion() : "")
				.cilindraje(vehiculo.getCilindraje()).avaluo(vehiculo.getAvaluo())
				.nombreMarca(vehiculo.getMarca().getNombre()).build();

	}

	@Override
	public Vehiculo buscarVehiculoPorId(BigInteger id) {
		return buscarVehiculoPorIdEnDB(id);
	}
}
