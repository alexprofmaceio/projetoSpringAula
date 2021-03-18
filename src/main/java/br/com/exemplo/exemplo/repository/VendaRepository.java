package br.com.exemplo.exemplo.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exemplo.exemplo.model.Venda;

public interface VendaRepository extends JpaRepository<Venda, Long> {

	Optional<Venda> findById(Long id);
	List<Venda> findByDataVenda(Date dataVenda);
}
