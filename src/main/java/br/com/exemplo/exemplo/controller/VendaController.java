package br.com.exemplo.exemplo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.exemplo.exemplo.model.Venda;
import br.com.exemplo.exemplo.repository.VendaRepository;

@Controller
public class VendaController {

	@Autowired
	VendaRepository vr;
	
	@GetMapping("/venda")
	public String exibeVenda(Model model) {
		List<Venda> vendas = new ArrayList<Venda>();
		vr.findAll().forEach(vendas::add);
		model.addAttribute("listaVendas", vendas);
		return "venda";
	}
	
	@GetMapping("/cadastrarVenda")
	public String cadastrarVenda(Model model) {
		Venda venda = new Venda();
		model.addAttribute("venda", venda);
		return "novaVenda";
	}
	
	@PostMapping("/salvarVenda")
	public String salvarVenda(@ModelAttribute("venda") Venda venda) {
		vr.save(venda);
		return "redirect:/venda";
	}
	
	@RequestMapping("/editarVenda/{id}")
	public ModelAndView editarVenda(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("editarVenda");
		Optional<Venda> venda = vr.findById(id);
		
		if(venda.isPresent()) {
			mv.addObject("venda", venda);
		}
		return mv;
	}
	
	@RequestMapping("/excluirVenda/{id}")
	public String excluirVenda(@PathVariable("id") Long id) {
		Optional<Venda> venda = vr.findById(id);
		if(venda.isPresent()) {
			vr.deleteById(id);
		}
		return "redirect:/venda";
	}
}
