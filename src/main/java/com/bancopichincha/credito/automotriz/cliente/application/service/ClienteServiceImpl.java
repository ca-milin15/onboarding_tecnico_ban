package com.bancopichincha.credito.automotriz.cliente.application.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;
import com.bancopichincha.credito.automotriz.cliente.domain.ClienteRepository;
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
public class ClienteServiceImpl implements ClienteService {

	ClienteRepository clienteRepository;
	PropiedadesSistema propiedadesSistema;

	@Override
	public Cliente procesoConsultarCliente(BigInteger id) {
		return consultarClienteEnDB(id);
	}

	private Cliente consultarClienteEnDB(BigInteger id) {
		return clienteRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String
				.format(propiedadesSistema.getExcepciones().getNegocio().getCliente().getEntidadNoEncontrada(), id)));
	}

	private List<Cliente> persistirEntidadesEnDB(List<Cliente> clientes) {
		try {
			return clienteRepository.saveAll(clientes);
		} catch (JpaSystemException | DataIntegrityViolationException e) {
			throw new IntegridadDatosRuntimeException(String.format(
					propiedadesSistema.getExcepciones().getNegocio().getCliente().getErrorIntegridadDatosAlmacenar(),
					e.getCause().getCause().getMessage()));
		} catch (ConstraintViolationException e) {
			throw new ValidacionCamposRuntimeException(e);
		} catch (Exception e) {
			throw new ErrorTransaccionalDBRuntimeException(
					propiedadesSistema.getExcepciones().getGeneral().getErrorTransaccionDb());
		}
	}

	@Override
	public void procesoAlmacenarCargaClientes(List<Cliente> listaClientes) {
		persistirEntidadesEnDB(listaClientes);
	}
}
