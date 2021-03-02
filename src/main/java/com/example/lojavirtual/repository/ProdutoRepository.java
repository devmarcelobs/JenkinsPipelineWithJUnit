package com.example.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lojavirtual.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	Produto findByCodigoBarra(int codigoBarra);
}
