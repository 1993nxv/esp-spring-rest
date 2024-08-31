package com.algaworks.algafood.domain.model.modelDTO;

import java.time.OffsetDateTime;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "pedidos-status")
@Getter
@Setter
public class PedidoStatusDTO extends RepresentationModel<PedidoStatusDTO>{
	
	private String codigo;
	
	private StatusPedido status;

	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	
}
