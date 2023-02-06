package com.bancopichincha.credito.automotriz.vehiculo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bancopichincha.credito.automotriz.marca.application.MarcaMediatorService;
import com.bancopichincha.credito.automotriz.marca.domain.Marca;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EntidadNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.EnumeracionNoEncontradaRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.IntegridadDatosRuntimeException;
import com.bancopichincha.credito.automotriz.shared.domain.exception.ValidacionCamposRuntimeException;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;
import com.bancopichincha.credito.automotriz.vehiculo.application.dto.VehiculoDTO;
import com.bancopichincha.credito.automotriz.vehiculo.application.service.VehiculoServiceImpl;
import com.bancopichincha.credito.automotriz.vehiculo.domain.Vehiculo;
import com.bancopichincha.credito.automotriz.vehiculo.domain.VehiculoRepository;

@SpringBootTest
public class VehiculoUnitTest {

	@Mock
	VehiculoRepository vehiculoRepositoryMocked;

	@Mock
	MarcaMediatorService marcaMediatorServiceMocked;

	@Mock
	PropiedadesSistema propiedadesSistemaMocked;

	VehiculoServiceImpl vehiculoServiceImpl;

	@BeforeEach
	void init() {
		vehiculoServiceImpl = new VehiculoServiceImpl(vehiculoRepositoryMocked, marcaMediatorServiceMocked,
				propiedadesSistemaMocked);
	}

	@Test
	private void procesoGuardarValidarQueMetodoGuardarVehiculoVehiculoRetorneExcepcionValidacionCampos() {
		var vehiculoReq = VehiculoDTO.builder().placa(null).modelo(1999).numeroChasis("2929292").cilindraje(1600)
				.avaluo(BigDecimal.valueOf(2300000L)).idMarca(BigInteger.valueOf(1L)).build();

		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class))).thenReturn(new Marca());
		Mockito.when(vehiculoRepositoryMocked.save(any(Vehiculo.class))).thenReturn(new Vehiculo());

		assertThatThrownBy(() -> vehiculoServiceImpl.guardarVehiculo(vehiculoReq))
				.isInstanceOf(ValidacionCamposRuntimeException.class);
	}

	@Test
	private void procesoGuardarValidarQueMetodoGuardarVehiculoRetorneExcepcionTipoVehiculoNoEncontrado() {
		var vehiculoReq = VehiculoDTO.builder().placa("FHH761").modelo(1999).numeroChasis("2929292").cilindraje(1600)
				.avaluo(BigDecimal.valueOf(2300000L)).idMarca(BigInteger.valueOf(1L)).tipo("XX").build();

		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class))).thenReturn(new Marca());

		assertThatThrownBy(() -> vehiculoServiceImpl.guardarVehiculo(vehiculoReq))
				.isInstanceOf(EnumeracionNoEncontradaRuntimeException.class);
	}

	@Test
	private void procesoGuardarValidarQueMetodoGuardarVehiculoRetorneExcepcionEntidadMarcaNoEncontrada() {
		var vehiculoReq = VehiculoDTO.builder().placa("FHH761").modelo(1999).numeroChasis("2929292").cilindraje(1600)
				.avaluo(BigDecimal.valueOf(2300000L)).idMarca(BigInteger.valueOf(1L)).tipo("XX").build();

		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class)))
				.thenThrow(EnumeracionNoEncontradaRuntimeException.class);

		assertThatThrownBy(() -> vehiculoServiceImpl.guardarVehiculo(vehiculoReq))
				.isInstanceOf(EnumeracionNoEncontradaRuntimeException.class);
	}

	@Test
	private void procesoGuardarValidarQueMetodoGuardarVehiculoExitosamente() {
		var vehiculoReq = VehiculoDTO.builder().placa("FHH761").modelo(1999).numeroChasis("2929292").cilindraje(1600)
				.avaluo(BigDecimal.valueOf(2300000L)).idMarca(BigInteger.valueOf(1L)).tipo("AUT").build();

		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class))).thenReturn(new Marca());
		Mockito.when(vehiculoRepositoryMocked.save(any(Vehiculo.class))).thenReturn(new Vehiculo());

		vehiculoServiceImpl.guardarVehiculo(vehiculoReq);

		verify(vehiculoRepositoryMocked, times(1)).save(any(Vehiculo.class));
	}

	@Test
	private void procesoActualizarValidarQueMetodoActualizarVehiculoCompletoExitosamente() {
		var vehiculoReq = VehiculoDTO.builder().placa("FHH761").modelo(1999).numeroChasis("2929292").cilindraje(1600)
				.avaluo(BigDecimal.valueOf(2300000L)).idMarca(BigInteger.valueOf(1L)).tipo("AUT").build();

		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class))).thenReturn(new Marca());
		Mockito.when(vehiculoRepositoryMocked.save(any(Vehiculo.class))).thenReturn(new Vehiculo());

		vehiculoServiceImpl.actualizarVehiculo(BigInteger.valueOf(1L), vehiculoReq);

		verify(vehiculoRepositoryMocked, times(1)).findById(any(BigInteger.class));
		verify(marcaMediatorServiceMocked, times(1)).buscarMarcaPorId(any(BigInteger.class));
		verify(vehiculoRepositoryMocked, times(1)).save(any(Vehiculo.class));
	}

	@Test
	private void procesoActualizarValidarQueMetodoActualizarVehiculoParcialExitosamente() {
		var vehiculoReq = VehiculoDTO.builder().modelo(2000).tipo("AUT").build();

		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		Mockito.when(marcaMediatorServiceMocked.buscarMarcaPorId(any(BigInteger.class))).thenReturn(new Marca());
		Mockito.when(vehiculoRepositoryMocked.save(any(Vehiculo.class))).thenReturn(new Vehiculo());

		vehiculoServiceImpl.actualizarVehiculo(BigInteger.valueOf(1L), vehiculoReq);

		verify(vehiculoRepositoryMocked, times(1)).findById(any(BigInteger.class));
		verify(marcaMediatorServiceMocked, times(0)).buscarMarcaPorId(any(BigInteger.class));
		verify(vehiculoRepositoryMocked, times(1)).save(any(Vehiculo.class));
	}

	@Test
	private void procesoEliminarValidarQueMetodoEliminarVehiculoRetorneEntidadNoEncontrada() {
		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class)))
				.thenThrow(EntidadNoEncontradaRuntimeException.class);

		assertThatThrownBy(() -> vehiculoServiceImpl.eliminarVehiculo(BigInteger.valueOf(1L)))
				.isInstanceOf(EntidadNoEncontradaRuntimeException.class);
	}

	@Test
	private void procesoEliminarValidarQueMetodoEliminarRetorneErrorIntegridadDatos() {

		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		doThrow(new IntegridadDatosRuntimeException("")).when(vehiculoRepositoryMocked).delete(any(Vehiculo.class));
		assertThatThrownBy(() -> vehiculoServiceImpl.eliminarVehiculo(BigInteger.valueOf(1L)))
				.isInstanceOf(IntegridadDatosRuntimeException.class);
	}

	@Test
	private void procesoEliminarValidarQueMetodoEliminarVehiculoExitoso() {

		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		vehiculoServiceImpl.eliminarVehiculo(any(BigInteger.class));
		verify(vehiculoRepositoryMocked, times(1)).findById(any(BigInteger.class));
		verify(vehiculoRepositoryMocked, times(1)).delete(any(Vehiculo.class));
	}

	@Test
	private void procesoBuscarPorIdValidarQueMetodoBusquedaVehiculoExitosa() {
		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		vehiculoServiceImpl.buscarVehiculoPorParams(1, BigInteger.valueOf(1L), 1);
		verify(vehiculoRepositoryMocked, times(1)).findById(any(BigInteger.class));
	}

	@Test
	private void procesoBuscarPorIdValidarQueMetodoBusquedaRetorneEntidadNoEncontrada() {
		Mockito.when(vehiculoRepositoryMocked.findById(any(BigInteger.class))).thenReturn(Optional.of(new Vehiculo()));
		doThrow(new EntidadNoEncontradaRuntimeException("")).when(vehiculoRepositoryMocked)
				.findById(any(BigInteger.class));
		assertThatThrownBy(() -> vehiculoServiceImpl.buscarVehiculoPorParams(1, BigInteger.valueOf(1L), 1))
				.isInstanceOf(EntidadNoEncontradaRuntimeException.class);
	}
}
