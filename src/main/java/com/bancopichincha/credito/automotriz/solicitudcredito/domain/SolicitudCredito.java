package com.bancopichincha.credito.automotriz.solicitudcredito.domain;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;
import com.bancopichincha.credito.automotriz.ejecutivo.domain.Ejecutivo;
import com.bancopichincha.credito.automotriz.shared.domain.EntidadGeneral;
import com.bancopichincha.credito.automotriz.solicitudcredito.application.convertersenum.ConverterEstadoSolicitudEnum;
import com.bancopichincha.credito.automotriz.solicitudcredito.application.enumeration.EstadoSolicitudEnum;
import com.bancopichincha.credito.automotriz.vehiculo.domain.Vehiculo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solicitud_credito")
public class SolicitudCredito  extends EntidadGeneral {

	private static final long serialVersionUID = 6776388846912535619L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Vehiculo vehiculo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Ejecutivo ejecutivo;
	
	@Column(name = "mesesPlazo")
	private int mesesPlazo;

	@Column(name = "cuotas")
	private int cuotas;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "estado")
	@Convert(converter = ConverterEstadoSolicitudEnum.class)
	private EstadoSolicitudEnum estado;
	
}
