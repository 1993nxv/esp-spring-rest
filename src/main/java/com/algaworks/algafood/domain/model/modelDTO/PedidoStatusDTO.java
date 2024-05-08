package com.algaworks.algafood.domain.model.modelDTO;

import java.time.OffsetDateTime;

import com.algaworks.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoStatusDTO {
	
	private Long id;
	
	private StatusPedido status;

	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataEntrega;
	private OffsetDateTime dataCancelamento;
	
}
