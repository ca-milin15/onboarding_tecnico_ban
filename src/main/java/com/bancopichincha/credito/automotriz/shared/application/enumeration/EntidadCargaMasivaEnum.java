package com.bancopichincha.credito.automotriz.shared.application.enumeration;

import lombok.Getter;

@Getter
public enum EntidadCargaMasivaEnum {

	CLIENTE("Cliente"), 
	MARCA("Marca"), 
	EJECUTIVO("Ejecutivo");

	private String descripcion;

	private EntidadCargaMasivaEnum(String descripcion) {
		this.descripcion = descripcion;
	}

}
