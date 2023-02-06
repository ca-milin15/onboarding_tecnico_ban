package com.bancopichincha.credito.automotriz.shared.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mensajes-sistema")
public class PropiedadesSistema {

	Excepcion excepciones;

	@Setter
	@Getter
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class Excepcion {

		General general;
		Negocio negocio;

		@Setter
		@Getter
		@FieldDefaults(level = AccessLevel.PRIVATE)
		public static class General {
			String enumeradoNoEncontrado;
			String errorTransaccionDb;
			String errorValidacionCamposEntidades;
			String errorDuplicadosMigracionDB;
		}

		@Setter
		@Getter
		@FieldDefaults(level = AccessLevel.PRIVATE)
		public static class Negocio {
			Vehiculo vehiculo;
			Marca marca;
			PatioAutos patioAutos;
			AsignacionCliente asignacionCliente;
			Cliente cliente;
			Ejecutivo ejecutivo;
			SolicitudCredito solicitudCredito;

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class SolicitudCredito {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
				String errorSolicitudesXDia;
				String errorVehiculoReservado;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class Ejecutivo {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class Cliente {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class AsignacionCliente {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class PatioAutos {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class Vehiculo {
				String errorIntegridadDatosAlmacenar;
				String entidadNoEncontrada;
			}

			@Setter
			@Getter
			@FieldDefaults(level = AccessLevel.PRIVATE)
			public static class Marca {
				String entidadNoEncontrada;
			}
		}
	}

}
