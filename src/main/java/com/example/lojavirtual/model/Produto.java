package com.example.lojavirtual.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Produto implements Serializable{

	@Id
	@Column(name="codigoBarra", nullable=false)
	private int codigoBarra;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="categoria", nullable=false)
	private String categoria;
	
	@Column(name="valor", nullable=false)
	private Double valor;

	public int getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(int codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
