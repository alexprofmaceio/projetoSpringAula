package br.com.exemplo.exemplo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.exemplo.exemplo.model.Permissao;
import br.com.exemplo.exemplo.model.Usuario;
import br.com.exemplo.exemplo.repository.PermissaoRepository;
import br.com.exemplo.exemplo.repository.UsuarioRepository;
import br.com.exemplo.exemplo.security.AutenticacaoService;

@Controller
public class SecurityController {

	@Autowired
	private UsuarioRepository ur;

	//@Autowired
	private PermissaoRepository pr;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	public static String getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  return ((UserDetails)principal).getUsername();
		} else {
			return principal.toString();
		}
	}
	
	@GetMapping("/login")  
    public String login(RedirectAttributes attr) {
		String username = getUsuarioLogado();
		Usuario usuario = ur.findByLogin(username);
		
		if (usuario == null) {
			return "login";
		} else {
			attr.addFlashAttribute("nome", usuario.getNome());
			return "redirect:/";
		}
	}
	
	@GetMapping("/usuario")
	public String usuario(Model model) {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		if(usuarios.isEmpty()) {
			model.addAttribute("usuarios", null);
		} else {
			ur.findAll().forEach(usuarios::add);
			model.addAttribute("usuarios", usuarios);
		}
		return "usuario";
	}
	
	@GetMapping("/novoUsuario")
	public String novoUsuario(Model model) {		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		return "novoUsuario";
	}
	
	@GetMapping("/novoUsuarioAdmin")
	public String novoUsuarioAdmin(Model model) {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		System.out.println("Permissões: "+permissoes);
		if(permissoes.isEmpty()) {
			model.addAttribute("permissoes", null);
		} else {
			pr.findAll().forEach(permissoes::add);
			model.addAttribute("permissoes", permissoes);
		}
		
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("permissoes", permissoes);
		return "novoUsuarioAdmin";
	}

	@PostMapping("/salvarUsuario")
	public String salvarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		
		Usuario u = ur.findByLogin(usuario.getLogin());
		if(u != null) {
			result.rejectValue("login", null, "Já existe cadastro com este e-mail");
		}
		
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagem","Erro ao gerar novo usuário.");
			return "novoUsuario";
		}
		
		autenticacaoService.salvar(usuario);
		attr.addFlashAttribute("mensagem","Usuário cadastrado.");
		return "redirect:/login";
	}

	@GetMapping("/permissao")
	public String exibePermissao(Model model) {
		List<Permissao> permissoes = new ArrayList<Permissao>();
		if(permissoes.isEmpty()) {
			model.addAttribute("permissoes", null);
		} else {
			pr.findAll().forEach(permissoes::add);
			model.addAttribute("permissoes", permissoes);
		}		
		return "permissao";
	}
	
	@GetMapping("/novaPermissao")
	public String novaPermissao(Model model) {		
		Permissao permissao = new Permissao();
		model.addAttribute("permissao", permissao);
		return "novaPermissao";
	}
		
	@PostMapping("/salvarPermissao")
	public String salvarPermissao(@Valid @ModelAttribute("permissao") Permissao permissao, RedirectAttributes attr) {
		try {
			pr.save(permissao);
			attr.addFlashAttribute("aviso", "sucesso salvar");
		} catch(Exception e) {
			attr.addFlashAttribute("aviso", "erro salvar");
		}
		return "redirect:/permissao";
	}
	
	@GetMapping("/esqueciSenha")
	public String esqueciSenha() {
		
		return "esqueciSenha";
	}	
}
