package br.com.exemplo.exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exemplo.exemplo.model.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long>{

	Permissao findByNome(String nome);
}
