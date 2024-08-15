package com.algaworks.algafood.domain.model.modelDTO;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteNomesDTO {
	
	@JsonView({Restaurante.class, RestauranteNomesDTO.class})
	private String nome;

}
