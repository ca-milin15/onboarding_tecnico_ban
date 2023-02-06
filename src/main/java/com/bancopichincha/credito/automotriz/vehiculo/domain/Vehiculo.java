package com.bancopichincha.credito.automotriz.vehiculo.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bancopichincha.credito.automotriz.marca.domain.Marca;
import com.bancopichincha.credito.automotriz.shared.domain.EntidadGeneral;
import com.bancopichincha.credito.automotriz.solicitudcredito.domain.SolicitudCredito;
import com.bancopichincha.credito.automotriz.vehiculo.application.converterenums.ConverterTipoVehiculoEnum;
import com.bancopichincha.credito.automotriz.vehiculo.application.enumerations.TipoVehiculoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vehiculo")
public class Vehiculo extends EntidadGeneral {

	private static final long serialVersionUID = -4099870549966644611L;

	
	@Column(name = "placa")
	@NotBlank
	private String placa;

	@NotNull
	@Column(name = "modelo")
	private Integer modelo;

	@NotBlank
	@Column(name = "numero_chasis")
	private String numeroChasis;

	@Column(name = "tipo")
	@Convert(converter = ConverterTipoVehiculoEnum.class)
	private TipoVehiculoEnum tipo;

	@NotNull
	@Column(name = "cilindraje")
	private Integer cilindraje;

	@NotNull
	@Column(name = "avaluo")
	private BigDecimal avaluo;

	@ManyToOne(fetch = FetchType.LAZY)
	private Marca marca;

	@OneToMany
	@JoinColumn(name = "vehiculo_id")
	private List<SolicitudCredito> listaSolicitudes;
}
