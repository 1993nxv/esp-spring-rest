package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;

@Component
public class CidadeDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTO cidadeDTOConverter(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toListDTO(List<Cidade> cidades){
		return cidades.stream()
				.map(cidade -> cidadeDTOConverter(cidade))
				.collect(Collectors.toList());
	}
}
