package com.bancopichincha.credito.automotriz.shared.application.service;

import com.bancopichincha.credito.automotriz.shared.infrastructure.MigracionDB;

public interface CargaMasivaMediatorService {

	void iniciarProcesoCargaMasiva(MigracionDB migracion);
}
