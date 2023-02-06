package com.bancopichincha.credito.automotriz.marca.application;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bancopichincha.credito.automotriz.marca.domain.Marca;
import com.bancopichincha.credito.automotriz.marca.domain.MarcaRepository;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MarcaServiceImpl implements MarcaMediatorService {

	MarcaRepository marcaRepository;
	PropiedadesSistema propiedadesSistema;

	@Override
	public Marca guardarMarcas(List<Marca> marca) {
		marcaRepository.saveAll(marca);
		return null;
	}

	@Override
	public Marca buscarMarcaPorId(BigInteger id) {
		return marcaRepository.findById(id).orElseThrow(() -> new EntidadNoEncontradaRuntimeException(String
				.format(propiedadesSistema.getExcepciones().getNegocio().getMarca().getEntidadNoEncontrada(), id)));
	}

}
