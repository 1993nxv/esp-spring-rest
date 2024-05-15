package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;

import com.algaworks.algafood.domain.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
	private Boolean aberto;
	private Boolean ativo;
	private EnderecoDTO endereco;
}
