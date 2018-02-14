package br.com.lucas.pedidos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Categoria;
import br.com.lucas.pedidos.repositories.CategoriaRepository;
import br.com.lucas.pedidos.services.exception.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Categoria find(Integer id) {
		Categoria obj = this.catRepo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: "+id + ", Tipo: "+Categoria.class.getName());			
		}
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return catRepo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		this.find(obj.getId());
		return catRepo.save(obj);
	}
}
