package com.bancopichincha.credito.automotriz.ejecutivo.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutos;
import com.bancopichincha.credito.automotriz.shared.domain.Persona;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCredito;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ejecutivo")
public class Ejecutivo extends Persona {

	private static final long serialVersionUID = -472186307912318700L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PatioAutos patio;

	@OneToMany
	@JoinColumn(name = "ejecutivo_id")
	private List<SolicitudCredito> listaSolicitudes;

	public Ejecutivo(String identificacion, String nombres, String apellidos, int edad, String direccion,
			String telefono, String celular, PatioAutos patio) {
		super(identificacion, nombres, apellidos, edad, direccion, telefono, celular);
		this.patio = patio;
	}
	
	
}
