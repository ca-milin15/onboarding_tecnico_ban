package com.bancopichincha.credito.automotriz.vehiculo.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface VehiculoCustomRepository {

	List<Vehiculo> buscarVehiculosPorParams(BigInteger marcaId, Integer modelo, LocalDateTime fechaInicio,
			LocalDateTime fechaFin);
}
