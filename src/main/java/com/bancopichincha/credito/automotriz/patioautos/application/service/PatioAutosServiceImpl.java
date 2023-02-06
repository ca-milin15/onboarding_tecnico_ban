package com.bancopichincha.credito.automotriz.patioautos.application.service;

import java.math.BigInteger;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancopichincha.credito.automotriz.patioautos.application.dto.PatioAutosDTO;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutos;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutosRepository;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ErrorTransaccionalDBRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;
import com.bancopichincha.credito.automotriz.shared.infrastructure.Utilidades;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatioAutosServiceImpl implements PatioAutosService {

	PatioAutosRepository patioAutosRepository;
	PropiedadesSistema propiedadesSistema;

	@Override
	public PatioAutosDTO procesoGuardar(PatioAutosDTO patioAutosDTO) {
		var patio = construirObjetoPatioAutosDesdeRequestCrear(patioAutosDTO);
		patio = persistirPatioAutos(patio);
		patioAutosDTO.setId(patio.getId());
		return patioAutosDTO;
	}

	private PatioAutos construirObjetoPatioAutosDesdeRequestCrear(PatioAutosDTO patioAutosDTO) {
		return PatioAutos.builder().nombre(patioAutosDTO.getNombre()).direccion(patioAutosDTO.getDireccion())
				.telefono(patioAutosDTO.getTelefono()).numeroPuntoVenta(patioAutosDTO.getNumeroPuntoVenta()).build();
	}

	@Transactional
	private PatioAutos persistirPatioAutos(PatioAutos patioAutos) {
		try {
			return patioAutosRepository.save(patioAutos);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(
					propiedadesSistema.getExcepciones().getNegocio().getPatioAutos().getErrorIntegridadDatosAlmacenar(),
					e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	@Override
	public PatioAutos buscarXIdRetornaEntidad(BigInteger id) {
		return buscarPorIdEnDB(id);
	}

	@Override
	public PatioAutosDTO procesoActualizar(BigInteger id, PatioAutosDTO patioAutosDTO) {
		var patio = buscarPorIdEnDB(id);
		patio = construirObjetoVehiculoDesdeRequestActualizar(patio, patioAutosDTO);
		patio = persistirPatioAutos(patio);
		return construirDTODesdeEntidad(patio);
	}

	private PatioAutosDTO construirDTODesdeEntidad(PatioAutos patio) {
		return PatioAutosDTO.builder().nombre(patio.getNombre()).direccion(patio.getDireccion())
				.telefono(patio.getTelefono()).numeroPuntoVenta(patio.getNumeroPuntoVenta()).build();
	}

	private PatioAutos construirObjetoVehiculoDesdeRequestActualizar(PatioAutos patioAutos,
			PatioAutosDTO patioAutosDTO) {
		var listaAttrNulos = Utilidades.obtenerAtributosconValorNull(patioAutosDTO);
		BeanUtils.copyProperties(patioAutosDTO, patioAutos, listaAttrNulos.toArray(new String[0]));
		return patioAutos;
	}

	private PatioAutos buscarPorIdEnDB(BigInteger id) {
		return patioAutosRepository.findById(id)
				.orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String.format(
						propiedadesSistema.getExcepciones().getNegocio().getPatioAutos().getEntidadNoEncontrada(),
						id)));
	}

	@Override
	@Transactional
	public boolean procesoEliminar(BigInteger id) {
		var patio = buscarPorIdEnDB(id);
		try {
			patioAutosRepository.delete(patio);
			return true;
		} catch (DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(propiedadesSistema.getExcepciones().getNegocio().getPatioAutos()
					.getErrorIntegridadDatosAlmacenar());
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	@Override
	public PatioAutosDTO buscarXId(BigInteger id) {
		return construirDTODesdeEntidad(buscarPorIdEnDB(id));
	}

}
