package br.com.exemplo.exemplo.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.exemplo.exemplo.model.Permissao;
import br.com.exemplo.exemplo.model.Usuario;
import br.com.exemplo.exemplo.repository.UsuarioRepository;

@Service
public class AutenticacaoService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
    public void salvar(Usuario usuario) {
    	Usuario u = new Usuario();
    	u.setNome(usuario.getNome());
    	u.setLogin(usuario.getLogin());
    	u.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
    	u.setPermissoes(Arrays.asList(new Permissao("ROLE_ADMIN")));
    	try {
        	usuarioRepository.save(u);
    	} catch(Exception e) {
    		System.out.println("Erro ao salvar: " + e);
    	}
    }

    public void salvarAdmin(Usuario usuario, String role) {
    	Usuario u = new Usuario();
    	u.setNome(usuario.getNome());
    	u.setLogin(usuario.getLogin());
    	u.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
    	u.setPermissoes(Arrays.asList(new Permissao(role)));
    	try {
        	usuarioRepository.save(u);
    	} catch(Exception e) {
    		System.out.println("Erro ao salvar: " + e);
    	}
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não localizado.");
		}
		return new User(usuario.getLogin(), usuario.getSenha(), mapRolesToAuthorities(usuario.getPermissoes()));		
	}
	
	private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection <Permissao> permissoes) {
        return permissoes.stream()
            .map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
            .collect(Collectors.toList());
    }
}
