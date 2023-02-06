package com.bancopichincha.credito.automotriz.patioautos.domain;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatioAutosRepository extends JpaRepository<PatioAutos, BigInteger> {

}
