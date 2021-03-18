package br.com.exemplo.exemplo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.exemplo.exemplo.model.Fornecedor;
import br.com.exemplo.exemplo.repository.FornecedorRepository;

@Controller
public class FornecedorController {

	@Autowired
	FornecedorRepository fr;

	@GetMapping("/fornecedor")
	public String exibeFornecedor(Model model) {
		List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		fr.findAll().forEach(fornecedores::add);

		if(fornecedores.isEmpty()) {
			model.addAttribute("fornecedores", null);
		} else {
			model.addAttribute("fornecedores", fornecedores);			
		}
		return "fornecedor";
	}
	
	@GetMapping("/cadastrarFornecedor")
	public String cadastrarFornecedor(Model model, RedirectAttributes attr) {
		
		Fornecedor fornecedor = new Fornecedor();
		try {
			model.addAttribute("fornecedor", fornecedor);
			attr.addFlashAttribute("aviso", "sucesso salvar");
		} catch(Exception e) {
			attr.addFlashAttribute("aviso", "erro salvar");
		}
		return "novoFornecedor";
	}
	
	@PostMapping("/salvarFornecedor")
	public String salvarFornecedor(@Valid @ModelAttribute("fornecedor") Fornecedor fornecedor, RedirectAttributes attr) {
		try {
			fr.save(fornecedor);
			attr.addFlashAttribute("aviso", "sucesso salvar");
		} catch(Exception e) {
			attr.addFlashAttribute("aviso", "erro salvar");
		}
		return "redirect:/fornecedor";
	}
	
	@RequestMapping("/editarFornecedor/{id}")
	public ModelAndView editarFornecedor(@PathVariable("id") Long id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("editarFornecedor");
		Optional<Fornecedor> fornecedor = fr.findById(id);
		
		if(fornecedor.isPresent()) {
			mv.addObject("fornecedor", fornecedor);
			attr.addFlashAttribute("aviso", "sucesso atualizar");
		} else {
			attr.addFlashAttribute("aviso", "erro atualizar");
		}
		return mv;
	}
	
	@RequestMapping("/excluirFornecedor/{id}")
	public String excluirFornecedor(@PathVariable("id") Long id, RedirectAttributes attr) {
		Optional<Fornecedor> fornecedor = fr.findById(id);
		
		if(fornecedor.isPresent()) {
			try {
				fr.deleteById(id);				
				attr.addFlashAttribute("aviso", "sucesso excluir");
			} catch(Exception e) {
				attr.addFlashAttribute("aviso", "erro integridade");
			}
		} else {
			attr.addFlashAttribute("aviso", "erro excluir");
		}
		return "redirect:/fornecedor";
	}
}
