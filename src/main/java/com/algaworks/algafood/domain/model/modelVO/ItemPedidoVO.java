package com.algaworks.algafood.domain.model.modelVO;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoVO {
	
	@Valid
	@NotNull
	private ProdutoIdVO produto;
	
	@NotNull
	private BigDecimal quantidade;
	
	private String observacao;
	
}
