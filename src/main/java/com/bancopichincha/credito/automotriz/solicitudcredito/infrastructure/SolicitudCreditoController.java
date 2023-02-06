package com.bancopichincha.credito.automotriz.solicitudcredito.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancopichincha.credito.automotriz.solicitudcredito.application.application.dto.SolicitudCreditoDTO;
import com.bancopichincha.credito.automotriz.solicitudcredito.application.application.service.SolicitudCreditoMediatorService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("solicitud-credito")
public class SolicitudCreditoController {

	SolicitudCreditoMediatorService solicitudCreditoMediatorService;

	@PostMapping
	public ResponseEntity<SolicitudCreditoDTO> crear(
			@Validated(CrearSolicitudGroupValidation.class) @RequestBody SolicitudCreditoDTO solicitudCreditoDTO) {
		return new ResponseEntity<>(solicitudCreditoMediatorService.procesoGuardar(solicitudCreditoDTO), HttpStatus.OK);
	}
}
