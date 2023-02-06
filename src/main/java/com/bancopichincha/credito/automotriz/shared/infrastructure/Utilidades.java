package com.bancopichincha.credito.automotriz.shared.infrastructure;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

@Log4j2
@UtilityClass
public class Utilidades {

	public <T> List<String> obtenerAtributosconValorNull(T object) {
		var listaAtributosNulos = new ArrayList<String>();
		final String SERIAL_VERSION_UID = "serialVersionUID";

		var atributos = object.getClass().getDeclaredFields();
		Arrays.asList(atributos).forEach(atributo -> {
			try {
				var nombre = atributo.getName();
				if (!nombre.equals(SERIAL_VERSION_UID)) {
					var field = object.getClass().getDeclaredField(nombre);
					field.setAccessible(true);
					if (ObjectUtils.isEmpty(field.get(object))) {
						listaAtributosNulos.add(nombre);
					}
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				log.error(e.getMessage());
			}
		});
		return listaAtributosNulos;
	}

	static final String RUTA_ARCHIVO_MIGRACION_DB = "C:\\Users\\camilo.rivera\\Documents\\Proyectos\\Pichincha\\src\\main\\resources\\migracionDB.json";

	public MigracionDB cargarArchivoMigracionDB(ObjectMapper objectMapper) {
		
		var rutaArchivoMigracionDB = Paths.get(RUTA_ARCHIVO_MIGRACION_DB);
		var data = new StringBuilder();
		try (var reader = Files.newBufferedReader(rutaArchivoMigracionDB, Charset.forName("UTF-8"))) {
			MigracionDB migracionDB = null;
			var linea = new String();
			while ((linea = reader.readLine()) != null) {
				data.append(linea);
			}
			if (!ObjectUtils.isEmpty(data)) {
				migracionDB = objectMapper.readValue(data.toString(), MigracionDB.class);
			}
			return migracionDB;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
