package com.bancopichincha.credito.automotriz.shared.application.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bancopichincha.credito.automotriz.cliente.application.enumerations.EstadoCivilEnum;
import com.bancopichincha.credito.automotriz.cliente.application.service.ClienteService;
import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;
import com.bancopichincha.credito.automotriz.ejecutivo.application.service.EjecutivoMediatorService;
import com.bancopichincha.credito.automotriz.ejecutivo.domain.Ejecutivo;
import com.bancopichincha.credito.automotriz.marca.application.MarcaMediatorService;
import com.bancopichincha.credito.automotriz.marca.domain.Marca;
import com.bancopichincha.credito.automotriz.patioautos.application.service.PatioAutosService;
import com.bancopichincha.credito.automotriz.shared.application.enumeration.EntidadCargaMasivaEnum;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionRegistrosDuplicadosArchivoRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.MigracionDB;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CargaMasivaMediatorServiceImpl implements CargaMasivaMediatorService {

	ProcesoArchivoStrategy procesoArchivoStrategy;
	ClienteService clienteService;
	MarcaMediatorService marcaMediatorService;
	EjecutivoMediatorService ejecutivoMediatorService;
	PatioAutosService patioAutosService;
	ObjectMapper objectMapper;
	PropiedadesSistema propiedadesSistema;

	static final String RUTA_CARGA_MASIVA = "C:\\Users\\camilo.rivera\\Documents\\Proyectos\\Pichincha\\src\\main\\resources\\cargaInicial\\";
	static final String NOMBRE_CARGA_CLIENTES = "cargaClientes.xlsx";
	static final String NOMBRE_CARGA_EJECUTIVOS = "cargaEjecutivos.xlsx";
	static final String NOMBRE_CARGA_MARCAS = "cargaMarcas.xlsx";

	static final List<EntidadCargaMasivaEnum> LISTA_DE_CARGAS = Arrays.asList(EntidadCargaMasivaEnum.CLIENTE,
			EntidadCargaMasivaEnum.EJECUTIVO, EntidadCargaMasivaEnum.MARCA);

	@Override
	@Transactional
	public void iniciarProcesoCargaMasiva(MigracionDB migracion) {

		for (var entidadCargaMasivaEnum : LISTA_DE_CARGAS) {
			switch (entidadCargaMasivaEnum) {
			case CLIENTE:
				procesoCargaCliente(migracion);
				break;
			case EJECUTIVO:
				procesoCargaEjecutivo(migracion);
				break;
			case MARCA:
				procesoCargaMarca(migracion);
				break;
			default:
				break;
			}
		}
		migracion.persistirArchivo(objectMapper);
	}

	private void procesoCargaCliente(MigracionDB migracion) {
		if (!migracion.getCarga().isCliente()) {
			var matrizDatosCliente = procesoArchivoStrategy
					.procesamientoArchivoXlsx(RUTA_CARGA_MASIVA.concat(NOMBRE_CARGA_CLIENTES));
			validacionesMatriz(matrizDatosCliente);
			var listaClientes = convertirMatrizClienteAListaClientes(matrizDatosCliente);
			clienteService.procesoAlmacenarCargaClientes(listaClientes);
			migracion.getCarga().setCliente(true);
		}
	}

	static final String CARACTER_SEPARADOR_CONCATENACION_ENTIDADES = "|";

	private void validacionesMatriz(ArrayList<ArrayList<String>> matrizDatosCliente) {
		List<String> listaEntidadesConcatenadas = matrizDatosCliente.stream().map(fila -> fila.toString())
				.collect(Collectors.toList());
		var listaRepetidos = listaEntidadesConcatenadas.stream().distinct()
				.filter(entidad -> Collections.frequency(listaEntidadesConcatenadas, entidad) > 1)
				.collect(Collectors.toList());
		if (listaRepetidos.size() > 0) {
			throw new ValidacionRegistrosDuplicadosArchivoRuntimeException(
					String.format(propiedadesSistema.getExcepciones().getGeneral().getErrorDuplicadosMigracionDB(),
							String.valueOf(listaRepetidos.size())));
		}
	}

	private void procesoCargaEjecutivo(MigracionDB migracion) {
		if (!migracion.getCarga().isEjecutivo()) {
			var matrizDatosEjecutivo = procesoArchivoStrategy
					.procesamientoArchivoXlsx(RUTA_CARGA_MASIVA.concat(NOMBRE_CARGA_EJECUTIVOS));
			validacionesMatriz(matrizDatosEjecutivo);
			var listaEjecutivos = convertirMatrizEjecutivoAListaEjecutivos(matrizDatosEjecutivo);
			ejecutivoMediatorService.iniciarProcesoAlmacenarEjecutivos(listaEjecutivos);
			migracion.getCarga().setEjecutivo(true);
		}
	}

	private void procesoCargaMarca(MigracionDB migracion) {
		if (!migracion.getCarga().isMarca()) {
			var matrizDatosMarca = procesoArchivoStrategy
					.procesamientoArchivoXlsx(RUTA_CARGA_MASIVA.concat(NOMBRE_CARGA_MARCAS));
			validacionesMatriz(matrizDatosMarca);
			var listaMarcas = convertirMatrizMarcaAListaMarcas(matrizDatosMarca);
			marcaMediatorService.guardarMarcas(listaMarcas);
			migracion.getCarga().setMarca(true);
		}
	}

	private List<Marca> convertirMatrizMarcaAListaMarcas(ArrayList<ArrayList<String>> matrizDatosCliente) {
		return matrizDatosCliente.stream().map(fila -> {
			return Marca.builder().nombre(fila.get(0)).build();
		}).collect(Collectors.toList());
	}

	private List<Cliente> convertirMatrizClienteAListaClientes(ArrayList<ArrayList<String>> matrizDatosCliente) {
		return matrizDatosCliente.stream().map(fila -> {
			var fecha = LocalDate.parse(fila.get(3).replace("\"", ""));
			var fechaL = LocalDateTime.of(fecha, LocalTime.of(0, 0));
			return new Cliente(String.valueOf(Double.valueOf(fila.get(0)).intValue()), fila.get(1),
					Double.valueOf(fila.get(2)).intValue(), fechaL, fila.get(4), fila.get(5),
					String.valueOf(Double.valueOf(fila.get(6)).intValue()), fila.get(7),
					EstadoCivilEnum.getEnumByCode(fila.get(8), ""), fila.get(9), fila.get(10),
					Boolean.valueOf(fila.get(11)));
		}).collect(Collectors.toList());
	}

	private List<Ejecutivo> convertirMatrizEjecutivoAListaEjecutivos(ArrayList<ArrayList<String>> matrizDatosCliente) {

		return matrizDatosCliente.stream().map(fila -> {
			var idPatioNumero = Double.valueOf(fila.get(6)).intValue();
			var patio = patioAutosService.buscarXIdRetornaEntidad(BigInteger.valueOf(idPatioNumero));
			return new Ejecutivo(String.valueOf(Double.valueOf(fila.get(0)).intValue()), fila.get(1), fila.get(2),
					Double.valueOf(fila.get(7)).intValue(), fila.get(3),
					String.valueOf(Double.valueOf(fila.get(4)).intValue()), fila.get(5), patio);
		}).collect(Collectors.toList());
	}
}
