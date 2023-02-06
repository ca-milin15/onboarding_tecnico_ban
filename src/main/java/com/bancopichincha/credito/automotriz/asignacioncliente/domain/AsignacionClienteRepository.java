package com.bancopichincha.credito.automotriz.asignacioncliente.domain;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignacionClienteRepository extends JpaRepository<AsignacionCliente, BigInteger> {

}
