package com.bancopichincha.credito.automotriz.vehiculo.application.enumerations;

import java.util.Arrays;

import com.bancopichincha.credito.automotriz.shared.domain.exception.EnumeracionNoEncontradaRuntimeException;

import lombok.Getter;

@Getter
public enum TipoVehiculoEnum {

	AUTOMOVIL("AUT", "AUTOMOVIL"), CAMPERO("CAM", "CAMPERO");

	private String codigo;
	private String descripcion;
	private static final String enumName = "Tipo de vehiculo";

	private TipoVehiculoEnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}

	public static TipoVehiculoEnum getEnumByCode(String codigo, String mensajeEnumeradoError) {
		return Arrays.asList(TipoVehiculoEnum.values()).stream()
				.filter(enumeration -> enumeration.getCodigo().equals(codigo)).findFirst()
				.orElseThrow(() -> new EnumeracionNoEncontradaRuntimeException(
						String.format(mensajeEnumeradoError, enumName, codigo)));
	}

}
