package com.bancopichincha.credito.automotriz.asignacioncliente.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutos;
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
@Table(name = "asignacion_cliente")
public class AsignacionCliente extends EntidadGeneral {

	private static final long serialVersionUID = 8448313716036247509L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	private PatioAutos patioAutos;

}
