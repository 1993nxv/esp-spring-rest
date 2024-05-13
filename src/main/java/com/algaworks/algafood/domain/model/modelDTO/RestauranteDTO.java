package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	
	@JsonView(RestauranteView.Resumo.class)
	private Boolean aberto;
	private Boolean ativo;
	private EnderecoDTO endereco;
}
