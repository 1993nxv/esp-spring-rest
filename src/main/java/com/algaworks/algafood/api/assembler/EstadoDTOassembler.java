package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.modelDTO.EstadoDTO;

@Component
public class EstadoDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoDTO estadoDTOConverter(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	public List<EstadoDTO> toListDTO(List<Estado> estados){
		return estados.stream()
				.map(estado -> estadoDTOConverter(estado))
				.collect(Collectors.toList());
	}
}
