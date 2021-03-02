package com.example.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lojavirtual.model.Adm;

public interface AdmRepository  extends JpaRepository<Adm, Integer>{
	Boolean findByLogin(String login);
	
	Boolean findBySenha(String senha);
	
	Adm findById(int id);
}
