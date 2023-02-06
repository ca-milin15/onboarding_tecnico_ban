package com.bancopichincha.credito.automotriz.shared.domain.exception;

public class EnumeracionNoEncontradaRuntimeException extends RuntimeException{

	private static final long serialVersionUID = -769662771166986427L;

	public EnumeracionNoEncontradaRuntimeException() {
		super();
	}

	public EnumeracionNoEncontradaRuntimeException(String message) {
		super(message);
	}

}
