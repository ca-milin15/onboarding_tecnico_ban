package com.bancopichincha.credito.automotriz.vehiculo.infrastructure;

import java.math.BigInteger;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancopichincha.credito.automotriz.vehiculo.application.dto.VehiculoDTO;
import com.bancopichincha.credito.automotriz.vehiculo.application.service.VehiculoService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequestMapping("vehiculo")
public class VehiculoController {

	VehiculoService vehiculoService;

	@PostMapping
	public ResponseEntity<VehiculoDTO> crear(
			@Validated(CrearVehiculoGroupValidation.class) @RequestBody VehiculoDTO vehiculoRequest) {
		return new ResponseEntity<>(vehiculoService.guardarVehiculo(vehiculoRequest), HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<VehiculoDTO> actualizar(@PathVariable(name = "id") BigInteger id,
			@Validated(ActualizarVehiculoGroupValidation.class) @RequestBody VehiculoDTO vehiculoRequest) {
		return new ResponseEntity<>(vehiculoService.actualizarVehiculo(id, vehiculoRequest), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> eliminar(@PathVariable(name = "id") BigInteger id) {
		return new ResponseEntity<>(vehiculoService.eliminarVehiculo(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<VehiculoDTO>> buscarXParams(@RequestParam(name = "anio", required = false) Integer anio,
			@RequestParam(name = "marca", required = false) BigInteger idMarca,
			@RequestParam(name = "modelo", required = false) Integer modelo) {
		return new ResponseEntity<>(vehiculoService.buscarVehiculoPorParams(anio, idMarca, modelo), HttpStatus.OK);
	}
}
