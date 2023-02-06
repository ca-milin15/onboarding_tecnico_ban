package com.bancopichincha.credito.automotriz.cliente.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bancopichincha.credito.automotriz.asignacioncliente.domain.AsignacionCliente;
import com.bancopichincha.credito.automotriz.cliente.application.convertersenum.ConverterEstadoCivilEnum;
import com.bancopichincha.credito.automotriz.cliente.application.enumerations.EstadoCivilEnum;
import com.bancopichincha.credito.automotriz.shared.domain.Persona;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCredito;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cliente")
public class Cliente extends Persona {

	private static final long serialVersionUID = -4618400137846208299L;

	@Column(name = "fechaNacimiento")
	private LocalDateTime fechaNacimiento;

	@Column(name = "estadoCivil")
	@Convert(converter = ConverterEstadoCivilEnum.class)
	private EstadoCivilEnum estadoCivil;

	@Column(name = "identificacionConyugue")
	private String identificacionConyugue;

	@Column(name = "nombreConyugue")
	private String nombreConyugue;

	@Column(name = "sujetoCredito")
	private boolean sujetoCredito;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id")
	private List<AsignacionCliente> asignacion;

	@OneToMany
	@JoinColumn(name = "cliente_id")
	private List<SolicitudCredito> listaSolicitudes;

	public Cliente(String identificacion, String nombres, int edad, LocalDateTime fechaNacimiento, String apellidos,
			String direccion, String telefono, String celular, EstadoCivilEnum estadoCivil,
			String identificacionConyugue, String nombreConyugue, boolean sujetoCredito) {
		super(identificacion, nombres, apellidos, edad, direccion, telefono, celular);
		this.fechaNacimiento = fechaNacimiento;
		this.estadoCivil = estadoCivil;
		this.identificacionConyugue = identificacionConyugue;
		this.nombreConyugue = nombreConyugue;
		this.sujetoCredito = sujetoCredito;
	}

}
