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
		return validarConfirmacaoPedido(id, pedido);
	}
	
	@Transactional
	public Pedido pedidoEntregue(Long id) {
		Pedido pedido = findById(id);
		return validarPedidoEntregue(id, pedido);
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
	
	private Pedido validarConfirmacaoPedido(Long id, Pedido pedido) {
		if(pedido.getStatus() == StatusPedido.CRIADO) {
			pedido.setStatus(StatusPedido.CONFIRMADO);
			pedido.setDataConfirmacao(
					OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return findById(id);
		} else {
			throw new NegocioException(
					"Não é possível alterar o status do pedido com id: "
					+ id
					+ " do status: "
					+ pedido.getStatus()
					+ " para: "
					+ StatusPedido.CONFIRMADO
			);
		}
	}
	
	private Pedido validarPedidoEntregue(Long id, Pedido pedido) {
		if(pedido.getStatus() == StatusPedido.CONFIRMADO) {
			pedido.setStatus(StatusPedido.ENTREGUE);
			pedido.setDataConfirmacao(
					OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			return findById(id);
		} else {
			throw new NegocioException(
					"Não é possível alterar o status do pedido com id: "
					+ id
					+ " do status: "
					+ pedido.getStatus()
					+ " para: "
					+ StatusPedido.ENTREGUE
			);
		}
	}

}
	

