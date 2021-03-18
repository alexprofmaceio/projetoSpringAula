package br.com.exemplo.exemplo.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Permissao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;

	//construtor
	public Permissao() {
		
	}
	
	//construtor implementa o atribuno nome
	public Permissao(String nome) {
		this.nome = nome;
	}

	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNome() {
	    return nome;
	}
	
	public void setNome(String nome) {
	    this.nome = nome;
	}	
}
