package com.bancopichincha.credito.automotriz.cliente.application.enumerations;

import java.util.Arrays;

import com.bancopichincha.credito.automotriz.shared.domain.exception.EnumeracionNoEncontradaRuntimeException;

public enum EstadoCivilEnum {

	CASADO("CA", "Casado"), UNION_LIBRE("UL", "Union libre"), SOLTERO("SO", "Soltero");

	private String codigo;
	private String descripcion;
	private static final String enumName = "Estado Civil";

	private EstadoCivilEnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public static EstadoCivilEnum getEnumByCode(String codigo, String mensajeEnumeradoError) {
		return Arrays.asList(EstadoCivilEnum.values()).stream()
				.filter(enumeration -> enumeration.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(() -> new EnumeracionNoEncontradaRuntimeException(
						String.format(mensajeEnumeradoError, enumName, codigo)));
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
