package com.bancopichincha.credito.automotriz.solicitudcredito.domain;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bancopichincha.credito.automotriz.solicitudcredito.application.enumeration.EstadoSolicitudEnum;

@Repository
public interface SolicitudCreditoRepository extends JpaRepository<SolicitudCredito, BigInteger> {

	List<SolicitudCredito> findByClienteIdAndFechaBetweenAndEstado(BigInteger clienteId, LocalDateTime fechaIni,
			LocalDateTime fechaFin, EstadoSolicitudEnum estado);

	List<SolicitudCredito> findByVehiculoIdAndEstado(BigInteger vehiculoId, EstadoSolicitudEnum estado);
}
