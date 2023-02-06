package com.bancopichincha.credito.automotriz.asignacioncliente.application.dto;

import java.math.BigInteger;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bancopichincha.credito.automotriz.asignacioncliente.infrastructure.CrearAsignacionGroupValidation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AsignacionClienteDTO {

	BigInteger idAsignacion;

	@Min(value = 1L)
	@NotNull(groups = {CrearAsignacionGroupValidation.class})
	BigInteger idPatio;

	@Min(value = 1L)
	@NotNull(groups = {CrearAsignacionGroupValidation.class})
	BigInteger idCliente;
	
	String nombrePatio;
	String nombreCliente;
}
