package com.bancopichincha.credito.automotriz;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bancopichincha.credito.automotriz.shared.application.service.CargaMasivaMediatorService;
import com.bancopichincha.credito.automotriz.shared.infrastructure.Utilidades;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class CaMsaCreditoautoApplication {

	@Autowired
	private CargaMasivaMediatorService cargaMasivaMediatorService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostConstruct
	public void iniciarCarga() {
		var migracion = Utilidades.cargarArchivoMigracionDB(objectMapper);
		cargaMasivaMediatorService.iniciarProcesoCargaMasiva(migracion);

	}

	public static void main(String[] args) {
		SpringApplication.run(CaMsaCreditoautoApplication.class, args);
	}


}
