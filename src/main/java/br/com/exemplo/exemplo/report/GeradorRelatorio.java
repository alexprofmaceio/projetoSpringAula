package br.com.exemplo.exemplo.report;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorRelatorio {

	private Document doc = new Document(PageSize.A4, 25, 25, 25, 25);
	
	public void pdfNovo(String arquivo) throws IOException, DocumentException {
		String caminho = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\relatorios\\" + arquivo;

		PdfWriter.getInstance(doc, new FileOutputStream(caminho));
		doc.open();		
	}
	
	public void pdfAddObjeto(Element[] objetos) throws DocumentException {
		for(int i = 0; i < objetos.length; i++) {
			doc.add(objetos[i]);
		}
	}

	public void pdfCabecalho(String titulo, String subTitulo) throws IOException, DocumentException {
		pdfNovo("listaProdutos.pdf");

		Font textoTitulo1 = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.BLACK);
		Font textoTitulo2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);

		Paragraph tituloCabecalho = new Paragraph(titulo, textoTitulo1);
		tituloCabecalho.setAlignment(Element.ALIGN_CENTER);

		Paragraph subTituloCabecalho = new Paragraph(subTitulo, textoTitulo2);
		subTituloCabecalho.setAlignment(Element.ALIGN_CENTER);

		Element[] objetos = new Element[] {tituloCabecalho, subTituloCabecalho, new Paragraph("  ")};
		
		pdfAddObjeto(objetos);
	}
	
	public void pdfTabela(String[] args, StringBuilder dados) throws DocumentException {		
		PdfPTable tabela = new PdfPTable(args.length);
		
		String[] dt = dados.toString().split(",");

		for(int i = 0; i < args.length; i++) {
			tabela.addCell(args[i]);
		}
		for(int j = 0; j < args.length; j++) {
				tabela.addCell(dt[j]);
		}

		Element[] objetos = new Element[] {tabela};
		pdfAddObjeto(objetos);		
	}

	public void pdfQRcode(String dados, int posLinha, int posColuna, int escala) throws DocumentException {	
		BarcodeQRCode qrcode = new BarcodeQRCode(dados.trim(), 1, 1, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(posColuna, posLinha);	//posição em pixels x,y quanto maior mais perto do cabecalho
        qrcodeImage.scalePercent(escala);

		Element[] objetos = new Element[] {qrcodeImage};
		pdfAddObjeto(objetos);
	}
	
	public void pdfImagem(String arquivo, int posLinha, int posColuna, int escalaX, int escalaY) throws MalformedURLException, IOException, DocumentException {
	
		String caminho = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\relatorios\\" + arquivo;
		Image imagem = Image.getInstance(caminho);
		imagem.scalePercent(escalaX, escalaY);
		imagem.setAbsolutePosition(posColuna, posLinha);
		
		Element[] objetos = new Element[] {imagem};
		pdfAddObjeto(objetos);
	}
	
	public void pdfRodape() throws DocumentException {
		int pagina = doc.getPageNumber() + 1;
		String p = "Página " + pagina;
		Font textoTitulo2 = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
		
		Paragraph rodape = new Paragraph(p, textoTitulo2);
		rodape.setAlignment(Element.ALIGN_CENTER);
		
		doc.add(new Paragraph(rodape));
		doc.close();
	}	
}
