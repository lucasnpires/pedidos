package br.com.lucas.pedidos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.lucas.pedidos.domain.Cidade;
import br.com.lucas.pedidos.domain.Cliente;
import br.com.lucas.pedidos.domain.Endereco;
import br.com.lucas.pedidos.domain.enums.TipoCliente;
import br.com.lucas.pedidos.dto.ClienteDTO;
import br.com.lucas.pedidos.dto.ClienteNewDTO;
import br.com.lucas.pedidos.repositories.CidadeRepository;
import br.com.lucas.pedidos.repositories.ClienteRepository;
import br.com.lucas.pedidos.repositories.EnderecoRepository;
import br.com.lucas.pedidos.services.exception.DataIntegrityException;
import br.com.lucas.pedidos.services.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepo.save(obj.getEnderecos());
		return obj;
	}
	
	public Cliente find(Integer id) {
		Cliente obj = this.repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado. Id: "+id + ", Tipo: "+Cliente.class.getName());			
		}
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		this.updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		this.find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um Cliente porque há entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO)  {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO)  {
		Cliente cli = new Cliente(null, objDTO.getNome(), 
										objDTO.getEmail(), 
										objDTO.getCpfOuCnpj(), 
										TipoCliente.toEnum(objDTO.getTipo()));
		
		Cidade cidade = cidadeRepo.findOne(objDTO.getCidadeId());
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), 
										  objDTO.getNumero(), 
										  objDTO.getComplemento(), 
										  objDTO.getBairro(), 
										  objDTO.getCep(), cli, cidade);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
}
