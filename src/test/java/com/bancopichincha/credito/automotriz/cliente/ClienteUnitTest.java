package com.bancopichincha.credito.automotriz.cliente;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bancopichincha.credito.automotriz.cliente.application.service.ClienteServiceImpl;
import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;
import com.bancopichincha.credito.automotriz.cliente.domain.ClienteRepository;
import com.bancopichincha.credito.automotriz.shared.infrastructure.PropiedadesSistema;

@SpringBootTest
public class ClienteUnitTest {

	@Mock
	ClienteRepository clienteRepository;

	@Mock
	PropiedadesSistema propiedadesSistemaMocked;

	ClienteServiceImpl clienteServiceImpl;

	@BeforeEach
	void init() {
		clienteServiceImpl = new ClienteServiceImpl(clienteRepository, propiedadesSistemaMocked);
	}

	@Test
	void procesoGuardarListadoClienteExitoso() {
		Mockito.when(clienteRepository.saveAll(new ArrayList<Cliente>())).thenReturn(null);
		clienteServiceImpl.procesoAlmacenarCargaClientes(new ArrayList<Cliente>());
		Mockito.verify(clienteRepository, times(1)).saveAll(new ArrayList<Cliente>());
	}

	@Test
	void procesoBuscarClientePorIdExitoso() {
		Mockito.when(clienteRepository.findById(any(BigInteger.class))).thenReturn(Optional.of(new Cliente()));
		clienteServiceImpl.procesoConsultarCliente(any(BigInteger.class));
		Mockito.verify(clienteRepository, times(1)).findById(any(BigInteger.class));
	}
}
