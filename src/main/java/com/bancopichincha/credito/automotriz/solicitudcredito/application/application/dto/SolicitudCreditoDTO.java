package com.bancopichincha.credito.automotriz.solicitudcredito.application.application.dto;

import java.math.BigInteger;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.bancopichincha.credito.automotriz.solicitudcredito.infrastructure.CrearSolicitudGroupValidation;

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
public class SolicitudCreditoDTO {
	
	BigInteger idSolicitudCredito;

	@NotNull(groups = CrearSolicitudGroupValidation.class)
	@Min(value = 1, groups = CrearSolicitudGroupValidation.class)
	BigInteger clienteId;

	@NotNull(groups = CrearSolicitudGroupValidation.class)
	@Min(value = 1, groups = CrearSolicitudGroupValidation.class)
	BigInteger vehiculoId;

	@NotNull(groups = CrearSolicitudGroupValidation.class)
	@Min(value = 1, groups = CrearSolicitudGroupValidation.class)
	Integer mesesPlazo;

	@NotNull(groups = CrearSolicitudGroupValidation.class)
	@Min(value = 1, groups = CrearSolicitudGroupValidation.class)
	Integer cuotas;

	@NotNull(groups = CrearSolicitudGroupValidation.class)
	@Min(value = 1, groups = CrearSolicitudGroupValidation.class)
	BigInteger ejecutivoId;

	String observacion;
}
