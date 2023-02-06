package com.bancopichincha.credito.automotriz.shared.infrastructure;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MigracionDB implements Serializable {

	private static final long serialVersionUID = 7052435204669342975L;

	Carga carga;

	@Getter
	@Setter
	@NoArgsConstructor
	@FieldDefaults(level = AccessLevel.PRIVATE)
	public static class Carga implements Serializable {

		private static final long serialVersionUID = 4190504351206100456L;

		boolean cliente;
		boolean ejecutivo;
		boolean marca;

	}

	public void persistirArchivo(ObjectMapper objectMapper) {
		final String RUTA_ARCHIVO_MIGRACION_DB = "C:\\Users\\camilo.rivera\\Documents\\Proyectos\\Pichincha\\src\\main\\resources\\migracionDB.json";
		try {
			var migracionAsString = objectMapper.writeValueAsString(this);
			Files.write(Paths.get(RUTA_ARCHIVO_MIGRACION_DB), migracionAsString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
