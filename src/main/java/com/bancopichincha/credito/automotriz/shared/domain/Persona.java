package com.bancopichincha.credito.automotriz.shared.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Persona extends EntidadGeneral {
	
	private static final long serialVersionUID = 337056593396492677L;

	@Column(name = "identificacion")
	private String identificacion;

	@Column(name = "nombres")
	private String nombres;

	@Column(name = "apellidos")
	private String apellidos;

	@Column(name = "edad")
	private int edad;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "celular")
	private String celular;
	
}
