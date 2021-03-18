package br.com.exemplo.exemplo.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull(message="Descrição não pode ser nulo")
	private String descricao;

	@NotNull(message="Quantidade não pode ser nulo")
	private float quantidade;
	
	@NotNull(message="Valor não pode ser nulo")
	private BigDecimal valor;
	
	@ManyToMany
	@JoinTable(
			name="produto_fornecedor", 
			joinColumns = {@JoinColumn(name="produtoId")}, 
			inverseJoinColumns = {@JoinColumn(name="fornecedorId")}
	)
	private List<Fornecedor> fornecedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Venda venda;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<Fornecedor> getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(List<Fornecedor> fornecedor) {
		this.fornecedor = fornecedor;
	}

	public float getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
}
