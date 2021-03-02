package com.example.lojavirtual;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.lojavirtual.controller.AdmController;
import com.example.lojavirtual.model.Adm;
import com.example.lojavirtual.model.Carrinho;
import com.example.lojavirtual.model.Cliente;
import com.example.lojavirtual.model.Produto;
import com.example.lojavirtual.repository.AdmRepository;
import com.example.lojavirtual.repository.ClienteRepository;
import com.example.lojavirtual.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PrincipalApplicationTests {
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	private void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Autowired
	private AdmRepository admRepository;
	
	//perguntar sobre testes multi parametrizados, como passar parametros de login e senha para o teste
	/*@Test
	public void testeLogin() throws Exception {
		Adm adm =  new Adm();
		adm.setLogin("teste");
		adm.setSenha("teste");
		
		admRepository.save(adm);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Adm/loginAdm")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		assertTrue(om.readValue(result.getResponse().getContentAsString(), Boolean.class) == Boolean.TRUE);
	}*/
	
	@Test
	public void testeCadastro_produto() throws Exception {
		Produto produto1 = new Produto();
		produto1.setCodigoBarra(123);
		produto1.setNome("teste");
		produto1.setCategoria("teste");
		produto1.setValor(100.00);
		
		String jsonRequest = om.writeValueAsString(produto1);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Adm/addProduto")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
			
		boolean x = om.readValue(result.getResponse().getContentAsString(), Boolean.class);
		
		assertTrue(x == Boolean.TRUE);
	}
	
	@Test
	public void testeCadastro_cliente() throws Exception{
		Cliente cliente = new Cliente();
		cliente.setCpf("238974892");
		cliente.setEndereco("teste");
		cliente.setNome("marcelo");
		
		String jsonRequest = om.writeValueAsString(cliente);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Adm/addCliente")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		boolean x = om.readValue(result.getResponse().getContentAsString(), Boolean.class);
		
		assertTrue(x == Boolean.TRUE);
	}
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Test
	public void testeAdicionaProduto_carrinho() throws Exception{
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
		
		produtoRepository.save(produto);
		
		Carrinho carrinho = new Carrinho();
		carrinho.setProdutos(produto);
		carrinho.setQuantidade(2);
		
		String jsonRequest = om.writeValueAsString(carrinho);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/Adm/addProdutoCarrinho")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		boolean x = om.readValue(result.getResponse().getContentAsString(), Boolean.class);
		
		assertTrue(x == Boolean.TRUE);
	}
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	public void testeListaCliente_cliente() throws Exception{
		Cliente cliente = new Cliente();
		cliente.setCpf("123");
		cliente.setEndereco("teste");
		cliente.setNome("teste");
		
		Cliente cliente2 = new Cliente();
		cliente2.setCpf("12333");
		cliente2.setEndereco("teste2");
		cliente2.setNome("teste2");
		
		clienteRepository.save(cliente);
		clienteRepository.save(cliente2);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/Adm/listaCliente")
				.content(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		
		assertTrue(om.readValue(result.getResponse().getContentAsString(), Boolean.class) == Boolean.TRUE);
	}
	
}
