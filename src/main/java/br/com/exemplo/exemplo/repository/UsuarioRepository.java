package br.com.exemplo.exemplo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.exemplo.exemplo.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findByLogin(String login);
}
