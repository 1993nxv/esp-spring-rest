package com.algaworks.algafood.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		usuarioService.findById(pedido.getCliente().getId());
		restauranteService.findById(pedido.getRestaurante().getId());
		validaProdutos(pedido);
		pedido = definirFrete(pedido);
		pedido = calcularValorTotal(pedido);
		
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
	
	public Pedido definirFrete(Pedido pedido) {
	    pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
	    return pedido;
	}
	
	public Pedido calcularValorTotal(Pedido pedido) {
	    pedido.setSubTotal(
	    	pedido.getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add)
	    	);
	    
	    pedido.setValorTotal(pedido.getSubTotal().add(pedido.getTaxaFrete()));
	    return pedido;
	}

	public Pedido atribuirPedidoAosItens(Pedido pedido) {
	    pedido.getItens().forEach(item -> item.setPedido(pedido));
	    return pedido;
	}
	
	public void validaProdutos(Pedido pedido) {
		pedido.getItens().forEach(item -> {
	        Produto produto = produtoService
	                .findProdutoByIdAndRestaurante(
	                		item.getProduto().getId(),
	                        pedido.getRestaurante().getId() 
	                );
		});
	}
	
	public Boolean validaFormaDePagamento(Pedido pedido) {
		Set<FormaPagamento> formasPagamento = restauranteService.findById(
				pedido.getRestaurante().getId())
					.getFormasPagamento();
		return formasPagamento.contains(pedido.getFormaPagamento());
	}

}
	

