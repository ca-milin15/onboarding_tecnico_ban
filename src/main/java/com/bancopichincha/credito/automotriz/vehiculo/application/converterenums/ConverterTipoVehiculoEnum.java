package com.bancopichincha.credito.automotriz.vehiculo.application.converterenums;

import javax.persistence.AttributeConverter;

import com.bancopichincha.credito.automotriz.vehiculo.application.enumerations.TipoVehiculoEnum;

public class ConverterTipoVehiculoEnum implements AttributeConverter<TipoVehiculoEnum, String>{

	@Override
	public String convertToDatabaseColumn(TipoVehiculoEnum attribute) {
		return attribute.getCodigo();
	}

	@Override
	public TipoVehiculoEnum convertToEntityAttribute(String valueAsString) {
		return TipoVehiculoEnum.getEnumByCode(valueAsString, "");
	}

}
