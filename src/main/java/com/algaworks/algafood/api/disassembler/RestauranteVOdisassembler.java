package com.algaworks.algafood.api.disassembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelVO.RestauranteVO;

@Component
public class RestauranteVOdisassembler {
	
	public Restaurante restauranteVOConverter(RestauranteVO restauranteVO) {
		Restaurante restaurante = new Restaurante();
		Cozinha cozinha = new Cozinha();
		
		cozinha.setId(restauranteVO.getCozinha().getId());
		
		restaurante.setNome(restauranteVO.getNome());
		restaurante.setTaxaFrete(restauranteVO.getTaxaFrete());
		restaurante.setCozinha(cozinha);
		return restaurante;
	}
}
