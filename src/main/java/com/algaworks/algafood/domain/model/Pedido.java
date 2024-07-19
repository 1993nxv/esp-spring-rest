package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo;
	
	private BigDecimal subTotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime(2)")
	private OffsetDateTime dataCriacao;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;
	
	public void calcularValorTotal() {
		this.setSubTotal(
	    	this.getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add)
	    	);
	    this.setValorTotal(this.getSubTotal().add(this.getTaxaFrete()));
	}
	
	public void confirmado() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		registerEvent(new PedidoConfirmadoEvent(this));
	}
	
	public void entregue() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
	}
	
	public void cancelado() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if(getStatus().naoPodeAlterarPara(novoStatus)){
			this.status = novoStatus;
		}else {
			throw new NegocioException(
				"Não é possível alterar o status do pedido com codigo: "
				+ this.getCodigo()
				+ " de: "
				+ this.getStatus().getDescricao()
				+ " para: "
				+ novoStatus.getDescricao()
			);
		}
	}
	
	@PrePersist
	private void gerarCodigo() {
		this.setCodigo(UUID.randomUUID().toString());
	}
}
