package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
	
	private Long id;
	
	private BigDecimal subTotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	private Usuario cliente;
	
//	private List<ItemPedido> itemPedido;
	
	private Endereco enderecoEntrega;
	
	private Restaurante restaurante;
	
	private FormaPagamento formaPagamento;
	
	private LocalDateTime dataCriacao;
	
	private LocalDateTime dataConfirmacao;
	
	private LocalDateTime dataCancelamento;
	
	private LocalDateTime dataEntrega;
	
//	private StatusPedido status;
	
}
