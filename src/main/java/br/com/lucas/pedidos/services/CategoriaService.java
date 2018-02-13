package br.com.lucas.pedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Categoria;
import br.com.lucas.pedidos.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = this.catRepo.findOne(id);
		return obj;
	}
}
