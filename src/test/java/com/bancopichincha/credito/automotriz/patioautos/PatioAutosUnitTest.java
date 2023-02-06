package com.bancopichincha.credito.automotriz.patioautos;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bancopichincha.credito.automotriz.patioautos.application.dto.PatioAutosDTO;
import com.bancopichincha.credito.automotriz.patioautos.application.service.PatioAutosServiceImpl;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutos;
import com.bancopichincha.credito.automotriz.patioautos.domain.PatioAutosRepository;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;

@SpringBootTest
public class PatioAutosUnitTest {

	@Mock
	PatioAutosRepository patioAutosRepositoryMocked;

	@Mock
	PropiedadesSistema propiedadesSistemaMocked;

	PatioAutosServiceImpl patioAutosServiceImpl;

	@BeforeEach
	void init() {
		patioAutosServiceImpl = new PatioAutosServiceImpl(patioAutosRepositoryMocked, propiedadesSistemaMocked);
	}

	@Test
	void procesoGuardarValidarAlmacenamietoFalidoPorCampoNombreNulos() {
		var patioAutos = PatioAutosDTO.builder().nombre(null).direccion("Cra4").telefono("4455")
				.numeroPuntoVenta("0099").build();
		assertThatThrownBy(() -> patioAutosServiceImpl.procesoGuardar(patioAutos))
				.isInstanceOf(ValidacionCamposRuntimeException.class);
	}

	@Test
	void procesoGuardarValidarAlmacenamietoFalidoPorCampoNumeroPuntoVentaVacio() {
		var patioAutos = PatioAutosDTO.builder().nombre("PuntoVenta Colombia").direccion("Cra4").telefono("4455")
				.numeroPuntoVenta("").build();
		assertThatThrownBy(() -> patioAutosServiceImpl.procesoGuardar(patioAutos))
				.isInstanceOf(ValidacionCamposRuntimeException.class);
	}

	@Test
	void procesoGuardarPatioAutosExitoso() {
		var patioAutos = PatioAutosDTO.builder().nombre("PuntoVenta Colombia").direccion("Cra4").telefono("4455")
				.numeroPuntoVenta("345345").build();
		Mockito.when(patioAutosRepositoryMocked.save(any(PatioAutos.class))).thenReturn(new PatioAutos());
		var patioAlmacenado = patioAutosServiceImpl.procesoGuardar(patioAutos);
		assertEquals(patioAlmacenado, PatioAutos.class);
		Mockito.verify(patioAutosRepositoryMocked, times(1)).save(any(PatioAutos.class));
	}

	@Test
	void procesoBuscarPatioPorIdExitoso() {
		Mockito.when(patioAutosRepositoryMocked.findById(any(BigInteger.class)))
				.thenReturn(Optional.of(new PatioAutos()));
		patioAutosServiceImpl.buscarXId(any(BigInteger.class));
		Mockito.verify(patioAutosRepositoryMocked, times(1)).findById(any(BigInteger.class));
	}

	@Test
	void procesoEliminarPatioPorIdExitoso() {
		Mockito.when(patioAutosRepositoryMocked.findById(any(BigInteger.class)))
				.thenReturn(Optional.of(new PatioAutos()));
		
		assertTrue(patioAutosServiceImpl.procesoEliminar(any(BigInteger.class)));
		Mockito.verify(patioAutosRepositoryMocked, times(1)).findById(any(BigInteger.class));
		Mockito.verify(patioAutosRepositoryMocked, times(1)).delete(new PatioAutos());
	}
}
