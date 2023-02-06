package com.bancopichincha.credito.automotriz.ejecutivo.domain;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjecutivoRepository extends JpaRepository<Ejecutivo, BigInteger> {

}
