package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Set;

import com.algaworks.algafood.core.security.AlgaSecutiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private CidadeService cidadeService;

	@Autowired
	private AlgaSecutiry algaSecutiry;
	
	
	public List<Pedido> findAll(){
		return pedidoRepository.findAll();
	}
	
	public Pedido findByCodigo(String codigo){
			return pedidoRepository.findByCodigo(codigo)
					.orElseThrow(() -> new PedidoNaoEncontradoException(codigo));	
	}

	@Transactional
	public Pedido save(Pedido pedido) {
		pedido = validarPedido(pedido);
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public Pedido confirmarPedido(String codigo) {
		Pedido pedido = findByCodigo(codigo);
		pedido.confirmado();
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public Pedido pedidoEntregue(String codigo) {
		Pedido pedido = findByCodigo(codigo);
		pedido.entregue();
		return findByCodigo(codigo);
	}
	
	@Transactional
	public Pedido cancelarPedido(String codigo) {
		Pedido pedido = findByCodigo(codigo);
		pedido.cancelado();
		return pedidoRepository.save(pedido);
	}

	private Pedido validarPedido(Pedido pedido) {
//		pedido.setCliente(usuarioService.findById(pedido.getCliente().getId()));
		pedido.setCliente(usuarioService.findById(algaSecutiry.getUsuarioId()));
		pedido.setRestaurante(restauranteService.findById(pedido.getRestaurante().getId()));
		pedido.getEnderecoEntrega().setCidade(cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId()));
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido = validarProdutos(pedido);
		pedido.calcularValorTotal();
		pedido = atribuirPedidoAosItens(pedido);
		validaFormaDePagamento(pedido);
		return pedido;
	}

	public Pedido validarProdutos(Pedido pedido) {
		pedido.getItens().forEach(item -> {
	        Produto produto = produtoService
	                .findProdutoByIdAndRestaurante(
	                		item.getProduto().getId(),
	                        pedido.getRestaurante().getId() 
	                );
	        item.setProduto(produto);
	        item.setPrecoTotal();
		});
		return pedido;
	}
	
	public Pedido atribuirPedidoAosItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> item.setPedido(pedido));
	    return pedido;
	}	
	
	public void validaFormaDePagamento(Pedido pedido) {
		Set<FormaPagamento> formasPagamento = restauranteService.findById(
				pedido.getRestaurante().getId()).getFormasPagamento();
		Boolean containsFormaPamento =  formasPagamento.contains(
				pedido.getFormaPagamento());
		if(!containsFormaPamento) {
			throw new FormaPagamentoNaoEncontradaException(
					"Forma de pagamento " 
					+ pedido.getFormaPagamento().getDescricao() 
					+ " não disponivel para o restaurante de id: " 
					+ pedido.getRestaurante().getId());
		}
	}
	
}
	

