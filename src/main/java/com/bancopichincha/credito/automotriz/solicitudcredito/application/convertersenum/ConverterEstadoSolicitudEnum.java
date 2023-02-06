package com.bancopichincha.credito.automotriz.solicitudcredito.application.convertersenum;

import javax.persistence.AttributeConverter;

import com.bancopichincha.credito.automotriz.solicitudcredito.application.enumeration.EstadoSolicitudEnum;

public class ConverterEstadoSolicitudEnum implements AttributeConverter<EstadoSolicitudEnum, String>{

	@Override
	public String convertToDatabaseColumn(EstadoSolicitudEnum estadoSolicitudEnum) {
		return estadoSolicitudEnum.getCodigo();
	}

	@Override
	public EstadoSolicitudEnum convertToEntityAttribute(String codigo) {
		return EstadoSolicitudEnum.getEnumByCode(codigo, "");
	}

}
