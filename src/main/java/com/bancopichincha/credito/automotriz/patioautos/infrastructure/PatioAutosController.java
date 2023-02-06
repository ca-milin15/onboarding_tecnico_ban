package com.bancopichincha.credito.automotriz.patioautos.infrastructure;

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

import com.bancopichincha.credito.automotriz.patioautos.application.dto.PatioAutosDTO;
import com.bancopichincha.credito.automotriz.patioautos.application.service.PatioAutosService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("patio-autos")
public class PatioAutosController {

	PatioAutosService patioAutosService;

	@PostMapping
	public ResponseEntity<PatioAutosDTO> crear(
			@Validated(CrearPatioAutosGroupValidation.class) @RequestBody PatioAutosDTO patioAutosDTO) {
		return new ResponseEntity<>(patioAutosService.procesoGuardar(patioAutosDTO), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<PatioAutosDTO> actualizar(@PathVariable(name = "id") BigInteger id,
			@RequestBody PatioAutosDTO patioAutosDTO) {
		return new ResponseEntity<>(patioAutosService.procesoActualizar(id, patioAutosDTO), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") BigInteger id) {
		return new ResponseEntity<>(patioAutosService.procesoEliminar(id), HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<PatioAutosDTO> buscarXId(@PathVariable(name = "id") BigInteger id) {
		return new ResponseEntity<>(patioAutosService.buscarXId(id), HttpStatus.OK);
	}
}
