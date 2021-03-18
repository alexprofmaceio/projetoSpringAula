package br.com.exemplo.exemplo.model;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nome;
	private String senha;
	private String login;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "usuarios_permissoes", 
			joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "permissao_id", referencedColumnName = "id")			
	)
	private Collection<Permissao> permissoes;

	public Usuario() {

	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Collection<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(Collection<Permissao> permissoes) {
		this.permissoes = permissoes;
	}	
}
