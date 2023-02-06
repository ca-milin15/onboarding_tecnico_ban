package com.bancopichincha.credito.automotriz.patioautos.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.asignacioncliente.domain.AsignacionCliente;
import com.bancopichincha.credito.automotriz.ejecutivo.domain.Ejecutivo;
import com.bancopichincha.credito.automotriz.shared.domain.EntidadGeneral;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patio_autos")
public class PatioAutos extends EntidadGeneral {

	private static final long serialVersionUID = 2127290206517838888L;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "telefono")
	private String telefono;

	@Column(name = "numeroPuntoVenta")
	private String numeroPuntoVenta;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "patio_id")
	private List<Ejecutivo> ejecutivos;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "patio_id")
	private List<AsignacionCliente> asignaciones;

}
