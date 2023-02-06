package com.bancopichincha.credito.automotriz.shared.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class EntidadGeneral implements Serializable{

	private static final long serialVersionUID = -5511554450776847596L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	BigInteger id;
	
	@Column(name = "fecha")
	LocalDateTime fecha = LocalDateTime.now();
	
}
