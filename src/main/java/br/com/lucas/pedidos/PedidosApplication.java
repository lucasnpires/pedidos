package br.com.lucas.pedidos;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.lucas.pedidos.domain.Categoria;
import br.com.lucas.pedidos.repositories.CategoriaRepository;

@SpringBootApplication
public class PedidosApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository catRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(PedidosApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		catRepo.save(Arrays.asList(cat1,cat2));
	}
}
