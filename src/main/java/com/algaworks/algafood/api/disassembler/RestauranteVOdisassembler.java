package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelVO.RestauranteVO;

@Component
public class RestauranteVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante restauranteVOConverter(RestauranteVO restauranteVO) {
		return modelMapper.map(restauranteVO, Restaurante.class);
	}
	
	public void copyToDomainObj(RestauranteVO restauranteVO, Restaurante restaurante) {
//		Evitando org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		modelMapper.map(restauranteVO, restaurante);
	}

}
