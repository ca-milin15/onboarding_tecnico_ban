package com.bancopichincha.credito.automotriz.marca.application;

import java.math.BigInteger;
import java.util.List;

import com.bancopichincha.credito.automotriz.marca.domain.Marca;

public interface MarcaMediatorService {

	Marca guardarMarcas(List<Marca> marca);
	
	Marca buscarMarcaPorId(BigInteger id);
}
