package com.bancopichincha.credito.automotriz.vehiculo.application.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.bancopichincha.credito.automotriz.vehiculo.infrastructure.CrearVehiculoGroupValidation;

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
public class VehiculoDTO {

	BigInteger id;
	
	@NotEmpty(groups = {CrearVehiculoGroupValidation.class})
	String placa;

	@NotNull(groups = {CrearVehiculoGroupValidation.class})
	Integer modelo;

	@NotEmpty(groups = {CrearVehiculoGroupValidation.class})
	String numeroChasis;

	@NotNull(groups = {CrearVehiculoGroupValidation.class})
	BigInteger idMarca;
	
	String nombreMarca;

	String tipo;

	@NotNull(groups = {CrearVehiculoGroupValidation.class})
	Integer cilindraje;

	@NotNull(groups = {CrearVehiculoGroupValidation.class})
	BigDecimal avaluo;
	
}
