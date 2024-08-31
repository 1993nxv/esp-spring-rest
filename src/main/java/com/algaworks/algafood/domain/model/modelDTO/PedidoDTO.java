package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos")
@Getter
@Setter
public class PedidoDTO extends RepresentationModel<PedidoDTO>{
	
	private String codigo;
	
	private UsuarioPedidoDTO cliente;
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	private RestaurantePedidoDTO restaurante;
	private List<ItemPedidoDTO> itens;
	
	private Endereco enderecoEntrega;
	private StatusPedido status;
	private FormaPagamento formaPagamento;
	
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
}
