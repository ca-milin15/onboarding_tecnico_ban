package com.bancopichincha.credito.automotriz.asignacioncliente.application.service;

import java.math.BigInteger;

import com.bancopichincha.credito.automotriz.asignacioncliente.application.dto.AsignacionClienteDTO;


public interface AsignacionClienteService {

	AsignacionClienteDTO procesoGuardar(AsignacionClienteDTO asignacionClienteDTO);

	AsignacionClienteDTO procesoActualizar(BigInteger id, AsignacionClienteDTO asignacionClienteDTO);

	boolean procesoEliminar(BigInteger id);

	AsignacionClienteDTO buscarXId(BigInteger id);
}
