package com.bancopichincha.credito.automotriz.vehiculo.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.BigIntegerType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VehiculoCustomRepositoryImpl implements VehiculoCustomRepository {

	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehiculo> buscarVehiculosPorParams(BigInteger marcaId, Integer modelo, LocalDateTime fechaInicio,
			LocalDateTime fechaFin) {

		var query = "SELECT v.id, v.placa, v.modelo, v.tipo, "
				+ "v.numero_chasis, v.cilindraje, v.avaluo, v.marca_id, v.fecha, m.nombre " + "FROM Vehiculo v "
				+ "JOIN Marca m ON m.id = v.marca_id " + "WHERE (:modelo IS NULL OR v.modelo = :modelo) "
				+ "and (:marcaId IS NULL OR v.marca_id = :marcaId) "
				+ "and (cast(:fechaIni as date) IS NULL OR v.fecha BETWEEN cast(:fechaIni as date) AND cast(:fechaFin as date)) ";

		var queryEntityMan = entityManager.createNativeQuery(query, Vehiculo.class);
		queryEntityMan.setParameter("modelo", new TypedParameterValue(IntegerType.INSTANCE, modelo));
		queryEntityMan.setParameter("marcaId", new TypedParameterValue(BigIntegerType.INSTANCE, marcaId));
		queryEntityMan.setParameter("fechaIni", new TypedParameterValue(LocalDateTimeType.INSTANCE, fechaInicio));
		queryEntityMan.setParameter("fechaFin", new TypedParameterValue(LocalDateTimeType.INSTANCE, fechaFin));

		return (List<Vehiculo>) queryEntityMan.getResultList();
	}

}
