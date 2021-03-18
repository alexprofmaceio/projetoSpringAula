package br.com.exemplo.exemplo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exemplo.exemplo.model.Fornecedor;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>{

	Optional<Fornecedor> findById(Long id);
	
	List<Fornecedor> findByNome(String nome);
}
