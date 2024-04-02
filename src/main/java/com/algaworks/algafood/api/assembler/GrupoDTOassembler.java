package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;

@Component
public class GrupoDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoDTO grupoDTOConverter(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toListDTO(List<Grupo> grupos){
		return grupos.stream()
				.map(Grupo -> grupoDTOConverter(Grupo))
				.collect(Collectors.toList());
	}
}
