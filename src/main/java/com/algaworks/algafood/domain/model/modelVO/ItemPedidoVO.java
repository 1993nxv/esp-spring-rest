package com.algaworks.algafood.domain.model.modelVO;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoVO {
	
	@NotNull
	private String produtoId;
	
	@NotNull
	private BigDecimal quantidade;
	
	private String observacao;
	
}
