package com.bancopichincha.credito.automotriz.shared.application.dto;

public class ControllerAdviceResponse {

	private String mensaje;

	public ControllerAdviceResponse(String mensaje) {
		super();
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
