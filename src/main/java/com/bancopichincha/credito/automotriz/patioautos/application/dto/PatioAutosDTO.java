package com.bancopichincha.credito.automotriz.patioautos.application.dto;

import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;

import com.bancopichincha.credito.automotriz.patioautos.infrastructure.CrearPatioAutosGroupValidation;

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
public class PatioAutosDTO {

	BigInteger id;

	@NotEmpty(groups = { CrearPatioAutosGroupValidation.class })
	String nombre;

	@NotEmpty(groups = { CrearPatioAutosGroupValidation.class })
	String direccion;

	@NotEmpty(groups = { CrearPatioAutosGroupValidation.class })
	String telefono;

	@NotEmpty(groups = { CrearPatioAutosGroupValidation.class })
	String numeroPuntoVenta;

}
