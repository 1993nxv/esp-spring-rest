package com.algaworks.algafood.domain.service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;

import ch.qos.logback.core.status.Status;

@Service
public class PedidoService {
	
	//private static final String MSG_PEDIDO_EM_USO = "Pedido com id:%d não pode ser removido, pois está em uso.";
	
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
	
	
	public List<Pedido> findAll(){
		return pedidoRepository.findAll();
	}
	
	public Pedido findById(Long id){
			return pedidoRepository.findById(id)
					.orElseThrow(() -> new PedidoNaoEncontradoException(id));	
	}

	@Transactional
	public Pedido save(Pedido pedido) {
		pedido = validarPedido(pedido);
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public Pedido confirmarPedido(Long id) {
		Pedido pedido = findById(id);
		pedido.confirmado();
		return findById(id);
	}
	
	@Transactional
	public Pedido pedidoEntregue(Long id) {
		Pedido pedido = findById(id);
		pedido.entregue();
		return findById(id);
	}
	
	@Transactional
	public Pedido cancelarPedido(Long id) {
		Pedido pedido = findById(id);
		pedido.cancelado();
		return findById(id);
	}

	private Pedido validarPedido(Pedido pedido) {
		pedido.setCliente(usuarioService.findById(pedido.getCliente().getId()));
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
	

