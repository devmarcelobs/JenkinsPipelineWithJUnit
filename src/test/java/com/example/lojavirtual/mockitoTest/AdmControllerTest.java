package com.example.lojavirtual.mockitoTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

//import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.lojavirtual.controller.AdmController;
import com.example.lojavirtual.model.Adm;
import com.example.lojavirtual.model.Carrinho;
import com.example.lojavirtual.model.Cliente;
import com.example.lojavirtual.model.Produto;
import com.example.lojavirtual.repository.AdmRepository;
import com.example.lojavirtual.repository.CarrinhoRepository;
import com.example.lojavirtual.repository.ClienteRepository;
import com.example.lojavirtual.repository.ProdutoRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdmControllerTest {
	@Mock
	private ProdutoRepository produtoRepository;
	
	@Mock
	private ClienteRepository clienteRepository;
	
	@Mock
	private AdmRepository admRepository;
	
	@Mock
	private CarrinhoRepository carrinhoRepository;
	
	/*@Rule
	public MockitoRule rule = MockitoJUnit.rule();*/
	
	@InjectMocks
	private AdmController admController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		admController = new AdmController(admRepository, produtoRepository, clienteRepository, carrinhoRepository);
	}
	
	@Test
	public void testeLogin_True() {
		Adm adm = new Adm();
		adm.setLogin("teste");
		adm.setSenha("teste");
		
		admRepository.save(adm);
		
		Mockito.when(admRepository.findById(adm.getId())).thenReturn(adm);
		
		assertEquals(Boolean.TRUE, admController.login("teste", "teste", adm));
	}
	
	@Test
	public void testeLogin_False() {
		Adm adm = new Adm();
		adm.setLogin("teste");
		adm.setSenha("teste");
		
		admRepository.save(adm);
		
		Mockito.when(admRepository.findById(adm.getId())).thenReturn(adm);
		
		//senha incorreta, ou login incorreto, ambos vão retornar false do controller
		assertEquals(Boolean.FALSE, admController.login("teste", "123", adm));
	}
	
	@Test
	public void testeCadastroProduto_True() {
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
		
		Mockito.when(produtoRepository.save(produto)).thenReturn(produto);
		
		assertEquals(Boolean.TRUE, admController.CadastroProduto(produto));
	}
	
	@Test
	public void testeCadastroProduto_False() {
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
		
		Mockito.when(produtoRepository.findByCodigoBarra(produto.getCodigoBarra())).thenReturn(produto);

		assertEquals(Boolean.FALSE, admController.CadastroProduto(produto));
	}
	
	@Test
	public void testeCadastroCliente_True() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123");
		cliente.setNome("teste");
		cliente.setEndereco("teste");
		
		Mockito.when(clienteRepository.save(cliente)).thenReturn(cliente);
		
		assertEquals(Boolean.TRUE, admController.CadastroCliente(cliente));
	}
	
	@Test
	public void testeCadastroCliente_False() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123");
		cliente.setNome("teste");
		cliente.setEndereco("teste");
		
		Mockito.when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(cliente);
		
		assertEquals(Boolean.FALSE, admController.CadastroCliente(cliente));
	}
	
	@Test
	public void testeAdicionaProdutoCarrinho_True() {
		//given
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
		
		produtoRepository.save(produto);
		
		Carrinho carrinho = new Carrinho();
		carrinho.setProdutos(produto);
		carrinho.setQuantidade(2);
		
		//when
		Mockito.when(produtoRepository.findByCodigoBarra(produto.getCodigoBarra())).thenReturn(produto);
		Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);
		
		//then
		//Mockito.verify(carrinhoRepository, Mockito.times(1)).save(Mockito.any(Carrinho.class));
		assertEquals(Boolean.TRUE, admController.AdicionarProdutoCarrinho(carrinho));
	}
	
	@Test
	public void testeAdiconaProdutoCarrinho_False() {
		//given
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
			
		Carrinho carrinho = new Carrinho();
		carrinho.setProdutos(produto);
		carrinho.setQuantidade(2);
		
		//when
		Mockito.when(carrinhoRepository.findById(carrinho.getId())).thenReturn(carrinho);
				
		//then
		assertEquals(Boolean.FALSE, admController.AdicionarProdutoCarrinho(carrinho));
	}
	
	@Test
	public void testeAdicionaProdutoCarrinhoRefatorado_True() {
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
				
		produtoRepository.save(produto);
				
		Carrinho carrinho = new Carrinho();
		carrinho.setProdutos(produto);
		carrinho.setQuantidade(2);
				
		Mockito.when(produtoRepository.findByCodigoBarra(produto.getCodigoBarra())).thenReturn(produto);
		Mockito.when(carrinhoRepository.save(Mockito.any(Carrinho.class))).thenReturn(carrinho);
				
		assertEquals(Boolean.TRUE, admController.AdicionarProdutoCarrinho(carrinho));
	}
	
	@Test
	public void testeAdicionaProdutoCarrinhoRefatorado_False() {
		Produto produto = new Produto();
		produto.setCodigoBarra(123);
		produto.setNome("teste");
		produto.setCategoria("teste");
		produto.setValor(100.00);
					
		Carrinho carrinho = new Carrinho();
		carrinho.setProdutos(produto);
		carrinho.setQuantidade(2);
				
		Mockito.when(carrinhoRepository.findById(carrinho.getId())).thenReturn(carrinho);
						
		assertEquals(Boolean.FALSE, admController.AdicionarProdutoCarrinho(carrinho));
	}
	
	@Test
	public void testeListaCliente_True() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123555");
		cliente.setEndereco("teste");
		cliente.setNome("teste");
		
		Cliente cliente2 = new Cliente();
		cliente2.setCpf("12333");
		cliente2.setEndereco("teste2");
		cliente2.setNome("teste2");
		
		List<Cliente> x = new ArrayList<>();
		x.add(cliente);
		x.add(cliente2);
		
		Mockito.when(clienteRepository.findAll()).thenReturn(x);
		
		assertEquals(Boolean.TRUE, admController.ListaCliente());
	}
	
	@Test
	public void testeListaCliente_False() {
		List<Cliente> x = new ArrayList<>();
		
		Mockito.when(clienteRepository.findAll()).thenReturn(x);
		
		assertEquals(Boolean.FALSE, admController.ListaCliente());
	}
}
