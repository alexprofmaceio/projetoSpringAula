package br.com.exemplo.exemplo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Integer quantVendida;
	private Date dataVenda;
	private Float totalVenda;

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getQuantVendida() {
		return quantVendida;
	}
	
	public void setQuantVendida(Integer quantVendida) {
		this.quantVendida = quantVendida;
	}
	
	public Date getDataVenda() {
		return dataVenda;
	}
	
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public Float getTotalVenda() {
		return totalVenda;
	}
	
	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}
}
