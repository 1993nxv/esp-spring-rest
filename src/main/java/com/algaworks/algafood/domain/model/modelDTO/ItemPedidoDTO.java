package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoDTO {
	private Long id;
	private ProdutoDTO produto;
	private BigDecimal quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
}
