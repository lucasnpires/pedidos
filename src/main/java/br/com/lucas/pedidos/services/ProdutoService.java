package br.com.lucas.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Categoria;
import br.com.lucas.pedidos.domain.Produto;
import br.com.lucas.pedidos.dto.ProdutoDTO;
import br.com.lucas.pedidos.repositories.CategoriaRepository;
import br.com.lucas.pedidos.repositories.ProdutoRepository;
import br.com.lucas.pedidos.services.exception.DataIntegrityException;
import br.com.lucas.pedidos.services.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository catRepo;
	
	public Produto find(Integer id) {
		Produto obj = this.repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: "+id + ", Tipo: "+Produto.class.getName());			
		}
		return obj;
	}
	
	public Produto insert(Produto obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Produto update(Produto obj) {
		Produto newObj = find(obj.getId());
		this.updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		this.find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Produto");
		}
	}
	
	public List<Produto> findAll(){
		return repo.findAll();
	}
	
	public Page<Produto> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Produto fromDTO(ProdutoDTO objDTO)  {
		return new Produto(objDTO.getId(), objDTO.getNome(), objDTO.getPreco());
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catRepo.findAll(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
	private void updateData(Produto newObj, Produto obj) {
		newObj.setNome(obj.getNome());
	}
}
