package com.algaworks.algafood.domain.model.modelVO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoVO {
	
	@NotNull
	private Long produtoId;
	
	@NotNull
	private BigDecimal quantidade;
	
	private String observacao;
	
}
