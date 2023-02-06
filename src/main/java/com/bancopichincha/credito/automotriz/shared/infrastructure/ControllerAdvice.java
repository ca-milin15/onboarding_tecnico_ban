package com.bancopichincha.credito.automotriz.shared.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.bancopichincha.credito.automotriz.shared.application.dto.ControllerAdviceResponse;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EnumeracionNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ErrorTransaccionalDBRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCreditoClienteXDiaXEstadoRuntimeException;

import lombok.AllArgsConstructor;

@org.springframework.web.bind.annotation.ControllerAdvice
@AllArgsConstructor
public class ControllerAdvice {

	PropiedadesSistema propiedadesSistema;

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ControllerAdviceResponse runtimeExceptionDefault(RuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

	@ExceptionHandler(EnumeracionNoEncontradaRuntimeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ControllerAdviceResponse tipoVehiculoNotFoundRuntimeException(RuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

	@ExceptionHandler(EntidadNoEncontradaRuntimeException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ControllerAdviceResponse entidadNoEncontradaRuntimeException(RuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

	@ExceptionHandler(ErrorTransaccionalDBRuntimeException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ControllerAdviceResponse errorTransaccionalDBRuntimeException(RuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

	@ExceptionHandler(IntegridadDatosRuntimeException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	@ResponseBody
	public ControllerAdviceResponse integridadDatosRuntimeException(RuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ControllerAdviceResponse methodArgumentNotValidException(MethodArgumentNotValidException ex,
			WebRequest request) {
		var mensajesPersonalizados = String.format(
				propiedadesSistema.getExcepciones().getGeneral().getErrorValidacionCamposEntidades(),
				ex.getFieldError().getField(), ex.getFieldError().getDefaultMessage());
		return new ControllerAdviceResponse(mensajesPersonalizados);
	}

	@ExceptionHandler(SolicitudCreditoClienteXDiaXEstadoRuntimeException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
	@ResponseBody
	public ControllerAdviceResponse solicitudCreditoClienteXDiaXEstadoRuntimeException(
			SolicitudCreditoClienteXDiaXEstadoRuntimeException ex, WebRequest request) {
		return new ControllerAdviceResponse(ex.getMessage());
	}

}
