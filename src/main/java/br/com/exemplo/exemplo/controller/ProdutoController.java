package br.com.exemplo.exemplo.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.exemplo.exemplo.model.Fornecedor;
import br.com.exemplo.exemplo.model.Produto;
import br.com.exemplo.exemplo.report.GeradorRelatorio;
import br.com.exemplo.exemplo.repository.FornecedorRepository;
import br.com.exemplo.exemplo.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	ProdutoRepository pr;
	
	@Autowired
	FornecedorRepository fr;

	GeradorRelatorio geradorRelatorio = new GeradorRelatorio();
	
	@GetMapping("/")
	public String homePage(Model model) {
		return "index";
	}
   
	@GetMapping("/pdfreport")
	public String relatorioProdutos(RedirectAttributes attr) throws IOException, DocumentException {
		List<Produto> produtos = pr.findAll();

		if(! produtos.isEmpty()) {
			int quantProdutos = produtos.size();
			String[] colunas = new String[] {"ID","PRODUTO","QUANT","VALOR"}; 

			geradorRelatorio.pdfCabecalho("SISTEMA DE CADASTRO", "Relação de Produtos Cadastrados");

			geradorRelatorio.pdfQRcode("Exemplo de QRCode", 600, 250, 200);
			
			geradorRelatorio.pdfImagem("imagem.png", 500, 200, 10, 10);
			
			final StringBuilder stringProdutos = new StringBuilder("");
			for(Produto produto : produtos) {
				stringProdutos.append(produto.getId()).append(",");
				stringProdutos.append(produto.getDescricao()).append(",");
				stringProdutos.append(Float.toString(produto.getQuantidade())).append(",");
				stringProdutos.append(produto.getValor().toString());
			}
			
			geradorRelatorio.pdfTabela(colunas, stringProdutos);
			geradorRelatorio.pdfRodape();
			
	        attr.addAttribute("mensagem", "Relatório gerado com sucesso!");			
		} else {
	        attr.addAttribute("mensagem", "Erro ao gerar o Relatório!");
		}
		
		return "redirect:/";
	}
	
	
/*	
	@GetMapping("/pdfreport")
	public String relatorioProdutos(RedirectAttributes attr) throws IOException, DocumentException {
		
		String caminho = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\relatorios\\" + "listaProdutos.pdf";
		List<Produto> produtos = pr.findAll();
		int quantProdutos = produtos.size();
		
		Document doc = new Document(PageSize.A4, 25, 25, 25, 25);
		PdfWriter.getInstance(doc, new FileOutputStream(caminho));
		doc.open();
		
		doc.newPage();
		
		Font textoTitulo1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.BLACK);
		Font textoTitulo2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
		
		Paragraph titulo = new Paragraph("SISTEMA DE CADASTRO", textoTitulo1);
		titulo.setAlignment(Element.ALIGN_CENTER);
		
		Paragraph subTitulo = new Paragraph("Relação de Produtos Cadastrados", textoTitulo2);
		subTitulo.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(titulo);
		doc.add(subTitulo);
		doc.add(new Paragraph("  "));
		
		PdfPTable tabela = new PdfPTable(4);
		tabela.addCell("ID");
		tabela.addCell("PRODUTO");		
		tabela.addCell("QUANT");		
		tabela.addCell("VALOR");		
		
		if(!produtos.isEmpty()) {
			for(Produto produto : produtos) {
				String quant = Float.toString(produto.getQuantidade());
				tabela.addCell(produto.getId().toString());
				tabela.addCell(produto.getDescricao());
				tabela.addCell(quant);
				tabela.addCell(produto.getValor().toString());
			}			
		} else {
			tabela.addCell(" ");
		}
		
		doc.add(tabela);
		int pagina = doc.getPageNumber() + 1;
		doc.add(new Paragraph("  "));
		String p = "Página " + pagina;
		
		Paragraph rodape = new Paragraph(p, textoTitulo2);
		rodape.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(new Paragraph(rodape));
		doc.close();		
        attr.addAttribute("mensagem", "Relatório gerado com sucesso!");
		return "redirect:/";
	}
*/
	
	@GetMapping("/produto")
	public String exibeProduto(Model model) {
		List<Produto> produtos = new ArrayList<Produto>();
		
		pr.findAll().forEach(produtos::add);
		
		if(produtos.isEmpty()) {
			model.addAttribute("produtos", null);
		} else {
			model.addAttribute("produtos", produtos);
		}
		return "produto";
	}
	
	@GetMapping("/cadastrarProduto")
	public String cadastrarProduto(Model model) {
		List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		fr.findAll().forEach(fornecedores::add);
		
		Produto produto = new Produto();
		
		model.addAttribute("produto", produto);
		model.addAttribute("fornecedores", fornecedores);
		return "novoProduto";
	}
	
	@PostMapping("/salvarProduto")
	public String salvarProduto(@Valid @ModelAttribute("produto") Produto produto, RedirectAttributes attr) {
		try {
			pr.save(produto);
			attr.addFlashAttribute("aviso", "sucesso salvar");
		} catch(Exception e) {
			attr.addFlashAttribute("aviso", "erro salvar");
		}
		return "redirect:/produto";
	}
	
	@RequestMapping("/editarProduto/{id}")
	public ModelAndView editarProduto(@PathVariable("id") Long id, RedirectAttributes attr) {
		ModelAndView mv = new ModelAndView("editarProduto");
		Optional<Produto> produto = pr.findById(id);
		
		if(produto.isPresent()) {
			mv.addObject("produto", produto);
			attr.addFlashAttribute("aviso", "sucesso atualizar");
		} else {
			attr.addFlashAttribute("aviso", "erro atualizar");
		}
		return mv;
	}
	
	@RequestMapping("/excluirProduto/{id}")
	public String excluirProduto(@PathVariable("id") Long id, RedirectAttributes attr) {
		Optional<Produto> produto = pr.findById(id);
		
		if(produto.isPresent()) {
			pr.deleteById(id);
			attr.addFlashAttribute("aviso", "sucesso excluir");
		} else {
			attr.addFlashAttribute("aviso", "erro excluir");
		}
		return "redirect:/produto";
	}
}
