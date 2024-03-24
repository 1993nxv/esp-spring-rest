package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;

@Component
public class CozinhaDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO cozinhaDTOConverter(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toListDTO(List<Cozinha> cozinhas){
		return cozinhas.stream()
				.map(cozinha -> cozinhaDTOConverter(cozinha))
				.collect(Collectors.toList());
	}
}
