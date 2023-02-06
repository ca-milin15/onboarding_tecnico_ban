package com.bancopichincha.credito.automotriz.vehiculo.domain;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, BigInteger>, VehiculoCustomRepository {

}
