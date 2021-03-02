package com.example.lojavirtual.model;

import org.springframework.web.bind.annotation.RequestBody;

public interface Login {
	public boolean login(String login, String senha, @RequestBody Adm adm);
}
