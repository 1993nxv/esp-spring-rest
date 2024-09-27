package com.algaworks.algafood.domain.model.modelVO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class PedidoVO {

	@Valid
	@NotNull
	private RestauranteIdVO restaurante;
	
	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoVO> itens;
	
	@Valid
	@NotNull
	private EnderecoVO enderecoEntrega;
	
	@Valid
	@NotNull
	private FormaPagamentoIdVO formaPagamento;
	
}
