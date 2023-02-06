package com.bancopichincha.credito.automotriz.vehiculo.application.service;

import java.math.BigInteger;
import java.util.List;

import com.bancopichincha.credito.automotriz.vehiculo.application.dto.VehiculoDTO;
import com.bancopichincha.credito.automotriz.vehiculo.domain.Vehiculo;

public interface VehiculoService {

	VehiculoDTO guardarVehiculo(VehiculoDTO vehiculoRequest);

	VehiculoDTO actualizarVehiculo(BigInteger id, VehiculoDTO vehiculoRequest);

	boolean eliminarVehiculo(BigInteger id);

	List<VehiculoDTO> buscarVehiculoPorParams(Integer anio, BigInteger marcaId, Integer modelo);

	Vehiculo buscarVehiculoPorId(BigInteger id);
}
