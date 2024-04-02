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
	
	public GrupoDTO GrupoDTOConverter(Grupo Grupo) {
		return modelMapper.map(Grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toListDTO(List<Grupo> Grupos){
		return Grupos.stream()
				.map(Grupo -> GrupoDTOConverter(Grupo))
				.collect(Collectors.toList());
	}
}
