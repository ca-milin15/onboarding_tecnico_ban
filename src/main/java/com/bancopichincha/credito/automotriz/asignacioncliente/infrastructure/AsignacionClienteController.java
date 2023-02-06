package com.bancopichincha.credito.automotriz.asignacioncliente.infrastructure;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancopichincha.credito.automotriz.asignacioncliente.application.dto.AsignacionClienteDTO;
import com.bancopichincha.credito.automotriz.asignacioncliente.application.service.AsignacionClienteService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("asignacion-cliente")
public class AsignacionClienteController {

	AsignacionClienteService asignacionClienteService;

	@PostMapping
	public ResponseEntity<AsignacionClienteDTO> crear(
			@Validated(CrearAsignacionGroupValidation.class) @RequestBody AsignacionClienteDTO asignacionClienteDTO) {
		return new ResponseEntity<>(asignacionClienteService.procesoGuardar(asignacionClienteDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<AsignacionClienteDTO> actualizar(@PathVariable(name = "id") BigInteger id,
			@RequestBody AsignacionClienteDTO asignacionClienteDTO) {
		return new ResponseEntity<>(asignacionClienteService.procesoActualizar(id, asignacionClienteDTO),
				HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") BigInteger id) {
		return new ResponseEntity<>(asignacionClienteService.procesoEliminar(id), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<AsignacionClienteDTO> buscarXId(@PathVariable(name = "id") BigInteger id) {
		return new ResponseEntity<>(asignacionClienteService.buscarXId(id), HttpStatus.OK);
	}
}
