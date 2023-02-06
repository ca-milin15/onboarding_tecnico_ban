package com.bancopichincha.credito.automotriz.cliente.application.convertersenum;

import javax.persistence.AttributeConverter;

import com.bancopichincha.credito.automotriz.cliente.application.enumerations.EstadoCivilEnum;

public class ConverterEstadoCivilEnum implements AttributeConverter<EstadoCivilEnum, String>{

	@Override
	public String convertToDatabaseColumn(EstadoCivilEnum attribute) {
		return attribute.getCodigo();
	}

	@Override
	public EstadoCivilEnum convertToEntityAttribute(String codigo) {
		return EstadoCivilEnum.getEnumByCode(codigo, "");
	}

}
