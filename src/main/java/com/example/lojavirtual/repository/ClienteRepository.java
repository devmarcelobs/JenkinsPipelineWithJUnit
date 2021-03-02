package com.example.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lojavirtual.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
	Cliente findByCpf(String cpf);
	
	//@Query("select new com.example.lojavirtual.model.Cliente (c.nome, c.endereco) " + "from Cliente c") é necessario uma query especificando os campos a serem retornados?
	List<Cliente> findAll();
}
