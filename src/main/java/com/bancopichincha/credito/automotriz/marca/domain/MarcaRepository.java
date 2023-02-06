package com.bancopichincha.credito.automotriz.marca.domain;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, BigInteger> {

}
