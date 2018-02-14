package br.com.lucas.pedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Cliente;
import br.com.lucas.pedidos.repositories.ClienteRepository;
import br.com.lucas.pedidos.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente find(Integer id) {
		Cliente obj = this.clienteRepository.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: "+id + ", Tipo: "+Cliente.class.getName());			
		}
		return obj;
	}
}
