package com.bancopichincha.credito.automotriz.marca.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.shared.domain.EntidadGeneral;
import com.bancopichincha.credito.automotriz.vehiculo.domain.Vehiculo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marca")
public class Marca extends EntidadGeneral {

	private static final long serialVersionUID = -136883353941407094L;

	@Column(name = "nombre")
	private String nombre;
	
	@OneToMany
	@JoinColumn(name = "idMarca")
	private List<Vehiculo> vehiculos;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
