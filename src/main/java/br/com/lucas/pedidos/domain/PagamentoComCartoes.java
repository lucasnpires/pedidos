package br.com.lucas.pedidos.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import br.com.lucas.pedidos.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartoes extends Pagamento implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	public PagamentoComCartoes() {
	}

	public PagamentoComCartoes(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas=numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
}
