package com.algaworks.algafood.domain.model.modelDTO;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	private Long id;
	private String nome;
	private BigDecimal precoFrete;
	private CozinhaDTO cozinha;
	
}
