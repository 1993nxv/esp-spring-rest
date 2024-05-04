package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Set;

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
	
	//private static final String MSG_PEDIDO_EM_USO = "Pedido com id:%d não pode ser removido, pois está em uso.";
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ProdutoService produtoService;
	
	
	public List<Pedido> findAll(){
		return pedidoRepository.findAll();
	}
	
	public Pedido findById(Long id){
			return pedidoRepository.findById(id)
					.orElseThrow(() -> new PedidoNaoEncontradoException(id));	
	}

	@Transactional
	public Pedido save(Pedido pedido) {
		pedido.setCliente(usuarioService.findById(pedido.getCliente().getId()));
		pedido.setRestaurante(restauranteService.findById(pedido.getRestaurante().getId()));
		pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
		pedido = validaProdutos(pedido);
		pedido.calcularValorTotal();
		pedido = atribuirPedidoAosItens(pedido);
		validaFormaDePagamento(pedido);
		
		return pedidoRepository.save(pedido);
	}
	
//	@Transactional
//	public Grupo updatePartially(Long id, Grupo grupo) {
//		findById(id);
//		grupo.setId(id);
//		return save(grupo);
//	}
//	
//	@Transactional
//	public void deleteById(Long id) {
//		grupoRepository.findById(id);
//		grupoRepository.flush();
//		try {
//			grupoRepository.deleteById(id);
//		} catch (DataIntegrityViolationException e) {
//			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
//		}	
//	}
	
//	public Pedido definirFrete(Pedido pedido) {
//	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
//	    return pedido;
//	}
	

	public Pedido atribuirPedidoAosItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> item.setPedido(pedido));
	    return pedido;
	}
	
	public Pedido validaProdutos(Pedido pedido) {
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
	
	public void validaFormaDePagamento(Pedido pedido) {
		Set<FormaPagamento> formasPagamento = restauranteService.findById(
				pedido.getRestaurante().getId())
					.getFormasPagamento();
		
		Boolean containsFormaPamento =  formasPagamento.contains(pedido.getFormaPagamento());
		if(!containsFormaPamento) {
			throw new FormaPagamentoNaoEncontradaException(
					"Forma de pagamento " 
					+ pedido.getFormaPagamento().getDescricao() 
					+ " não disponivel para o restaurante de id: " 
					+ pedido.getRestaurante().getId());
		}
	}

}
	

