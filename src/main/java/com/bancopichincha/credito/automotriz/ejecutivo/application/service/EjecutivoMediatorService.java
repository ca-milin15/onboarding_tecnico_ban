package com.bancopichincha.credito.automotriz.ejecutivo.application.service;

import java.math.BigInteger;
import java.util.List;

import com.bancopichincha.credito.automotriz.ejecutivo.domain.Ejecutivo;

public interface EjecutivoMediatorService {

	void iniciarProcesoAlmacenarEjecutivos(List<Ejecutivo> ejecutivos);
	
	Ejecutivo buscarEjecutivo (BigInteger id);
}
