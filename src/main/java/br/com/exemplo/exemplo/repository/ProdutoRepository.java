package br.com.exemplo.exemplo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exemplo.exemplo.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	Optional<Produto> findById(Long id);

}
