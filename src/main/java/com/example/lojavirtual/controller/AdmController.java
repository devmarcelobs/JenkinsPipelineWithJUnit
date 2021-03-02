package com.example.lojavirtual.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojavirtual.model.Adm;
import com.example.lojavirtual.model.Carrinho;
import com.example.lojavirtual.model.Cliente;
import com.example.lojavirtual.model.Login;
import com.example.lojavirtual.model.Produto;
import com.example.lojavirtual.repository.AdmRepository;
import com.example.lojavirtual.repository.CarrinhoRepository;
import com.example.lojavirtual.repository.ClienteRepository;
import com.example.lojavirtual.repository.ProdutoRepository;

@RestController
@RequestMapping("/Adm")
public class AdmController implements Login{
	//@Autowired
	private AdmRepository admRepository;
	//@Autowired
	private ProdutoRepository produtoRepository;
	//@Autowired
	private ClienteRepository clienteRepository;
	//@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	public AdmController(AdmRepository admRepository, ProdutoRepository produtoRepository, ClienteRepository clienteRepository, CarrinhoRepository carrinhoRepository) {
		this.admRepository = admRepository;
		this.produtoRepository = produtoRepository;
		this.clienteRepository = clienteRepository;
		this.carrinhoRepository = carrinhoRepository;
	}
	
	/*@Autowired
	public AdmController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}*/
	
	@GetMapping("/loginAdm")
	@Override
	public boolean login(String login, String senha, @RequestBody Adm adm) {
		if(admRepository.findById(adm.getId())!=null) {
			if(adm.getLogin().equals(login)) {
				if(adm.getSenha().equals(senha)) {
					System.out.println("Login realizado com sucesso");
					return true;
				}
			}
		}
		
		System.out.println("Usuário ou Senha incorretos");
		return false;
	}
	
	@PostMapping("/addProduto")
	@ResponseBody
	public boolean CadastroProduto(@RequestBody Produto produto) {
		if(produtoRepository.findByCodigoBarra(produto.getCodigoBarra())==null) {
			produtoRepository.save(produto);
			System.out.println("Produto cadastrado com sucesso");
			return true;
		}
		else {
			System.out.println("Codigo de Barras já existente");
			return false;
		}
	}
	
	@PostMapping("/addCliente")
	public boolean CadastroCliente(@RequestBody Cliente cliente) {
		if(clienteRepository.findByCpf(cliente.getCpf())==null) {
			clienteRepository.save(cliente);
			System.out.println("Cliente cadastrado com sucesso");
			return true;
		}
		else {
			System.out.println("CPF já existente");
			return false;
		}
	}
	
	@PostMapping("/addProdutoCarrinho")
	public boolean AdicionarProdutoCarrinho(@RequestBody Carrinho carrinho) {
		if(carrinhoRepository.findById(carrinho.getId())==null) {
			if(produtoRepository.findByCodigoBarra(carrinho.getProdutos().getCodigoBarra())!=null) {
				carrinhoRepository.save(carrinho);
				return true;
			}
		}
		System.out.println("Carrinho já criado");
		return false;
	}
	
	@PostMapping("/addProdutoCarrinhoRefatorado")
	public boolean AdicionarProdutoCarrinhoRefatorado(@RequestBody Carrinho carrinho) {
		if(carrinhoRepository.findById(carrinho.getId())==null) {
			Produto produto = carrinho.getProdutos();
			int codigoProdutoCarrinho  = produto.getCodigoBarra();
			
			if(produtoRepository.findByCodigoBarra(codigoProdutoCarrinho)!=null) {
				carrinhoRepository.save(carrinho);
				return true;
			}
		}
		System.out.println("Carrinho já criado");
		return false;
	}
	
	@GetMapping("/listaCliente")
	public boolean ListaCliente(){
		List<Cliente> lista = clienteRepository.findAll();
		if(lista.isEmpty()) {
			System.out.println("Lista de Clientes vazia");
			return false;
		}
		else {
			for(Cliente cliente : lista) {
				System.out.println("CPF: "+cliente.getCpf()+" | "+"Nome: "+cliente.getNome()+" | "+"Endereco: "+cliente.getEndereco());
			}
			return true;
		}
	}
	
}
