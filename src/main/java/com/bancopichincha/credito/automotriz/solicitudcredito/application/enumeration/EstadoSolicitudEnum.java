package com.bancopichincha.credito.automotriz.solicitudcredito.application.enumeration;

import java.util.Arrays;

import com.bancopichincha.credito.automotriz.shared.domain.exception.EnumeracionNoEncontradaRuntimeException;

public enum EstadoSolicitudEnum {
	
	REGISTRADA ("REG", "Registrada"),
	DESPACHADA ("DES", "Despachada"),
	CANCELADA ("CAN", "Cancelada");
	
	private String codigo;
	private String descripcion;
	private static final String enumName = "Estado solicitud";
	
	private EstadoSolicitudEnum(String codigo, String descripcion) {
		this.codigo = codigo;
		this.descripcion = descripcion;
	}
	
	public static EstadoSolicitudEnum getEnumByCode(String codigo, String mensajeEnumeradoError) {
		return Arrays.asList(EstadoSolicitudEnum.values()).stream()
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
