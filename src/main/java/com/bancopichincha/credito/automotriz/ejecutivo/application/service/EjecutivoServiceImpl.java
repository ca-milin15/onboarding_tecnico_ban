package com.bancopichincha.credito.automotriz.ejecutivo.application.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.bancopichincha.credito.automotriz.ejecutivo.domain.Ejecutivo;
import com.bancopichincha.credito.automotriz.ejecutivo.domain.EjecutivoRepository;
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
public class EjecutivoServiceImpl implements EjecutivoMediatorService {

	EjecutivoRepository ejecutivoRepository;
	PropiedadesSistema propiedadesSistema;

	@Override
	public void iniciarProcesoAlmacenarEjecutivos(List<Ejecutivo> ejecutivos) {
		persistirEntidadesEnDB(ejecutivos);
	}

	private List<Ejecutivo> persistirEntidadesEnDB(List<Ejecutivo> clientes) {
		try {
			return ejecutivoRepository.saveAll(clientes);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(
					propiedadesSistema.getExcepciones().getNegocio().getEjecutivo().getErrorIntegridadDatosAlmacenar(),
					e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	private Ejecutivo buscarPorIdEnDB(BigInteger id) {
		return ejecutivoRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String
				.format(propiedadesSistema.getExcepciones().getNegocio().getEjecutivo().getEntidadNoEncontrada(), id)));
	}

	@Override
	public Ejecutivo buscarEjecutivo(BigInteger id) {
		return buscarPorIdEnDB(id);
	}

}
