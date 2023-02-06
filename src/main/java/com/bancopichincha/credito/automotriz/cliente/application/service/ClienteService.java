package com.bancopichincha.credito.automotriz.cliente.application.service;

import java.math.BigInteger;
import java.util.List;

import com.bancopichincha.credito.automotriz.cliente.domain.Cliente;

public interface ClienteService {

	Cliente procesoConsultarCliente(BigInteger id);
	
	void procesoAlmacenarCargaClientes(List<Cliente> listaClientes);
}
