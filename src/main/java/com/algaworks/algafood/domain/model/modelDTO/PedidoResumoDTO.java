package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoResumoDTO {
	
	private Long id;
	
	private UsuarioPedidoDTO cliente;
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	private RestaurantePedidoDTO restaurante;

	private StatusPedido status;
	private OffsetDateTime dataCriacao;
	
}
