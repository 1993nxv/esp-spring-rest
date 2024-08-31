package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos-resumo")
@Getter
@Setter
public class PedidoResumoDTO extends RepresentationModel<PedidoResumoDTO>{
	
	private String codigo;
	
	private UsuarioPedidoDTO cliente;
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	private RestaurantePedidoDTO restaurante;

	private StatusPedido status;
	private OffsetDateTime dataCriacao;
	
}
