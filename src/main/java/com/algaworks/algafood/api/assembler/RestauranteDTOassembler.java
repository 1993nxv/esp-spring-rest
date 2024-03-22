package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteDTO;

@Component
public class RestauranteDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDTO restauranteDTOConverter(Restaurante restaurante) {
//		RestauranteDTO restauranteDTO = new RestauranteDTO();
//		CozinhaDTO cozinhaDTO = new CozinhaDTO();
//		
//		cozinhaDTO.setId(restaurante.getCozinha().getId());
//		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
//		
//		restauranteDTO.setId(restaurante.getId());
//		restauranteDTO.setNome(restaurante.getNome());
//		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
//		restauranteDTO.setCozinha(cozinhaDTO);
		
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}
	
	public List<RestauranteDTO> toListDTO(List<Restaurante> restaurantes){
		return restaurantes.stream()
				.map(restaurante -> restauranteDTOConverter(restaurante))
				.collect(Collectors.toList());
	}
}
