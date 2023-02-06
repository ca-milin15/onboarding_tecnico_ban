package com.bancopichincha.credito.automotriz.patioautos.application.service;

import java.math.BigInteger;

import com.bancopichincha.credito.automotriz.patioautos.application.dto.PatioAutosDTO;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutos;

public interface PatioAutosService {

	PatioAutosDTO procesoGuardar(PatioAutosDTO patioAutosDTO);

	PatioAutosDTO procesoActualizar(BigInteger id, PatioAutosDTO patioAutosDTO);

	boolean procesoEliminar(BigInteger id);

	PatioAutosDTO buscarXId(BigInteger id);

	PatioAutos buscarXIdRetornaEntidad(BigInteger id);

}
