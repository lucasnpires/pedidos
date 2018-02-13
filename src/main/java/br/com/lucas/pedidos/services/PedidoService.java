package br.com.lucas.pedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Pedido;
import br.com.lucas.pedidos.repositories.PedidoRepository;
import br.com.lucas.pedidos.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id) {
		Pedido obj = this.pedidoRepository.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: "+id + ", Tipo: "+Pedido.class.getName());			
		}
		return obj;
	}
}
