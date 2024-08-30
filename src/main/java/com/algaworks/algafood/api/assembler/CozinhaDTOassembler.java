package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;

@Component
public class CozinhaDTOassembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTOassembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}
	
	public CozinhaDTO cozinhaDTOConverter(Cozinha cozinha) {
		
		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaDTO);
		
		cozinhaDTO.add(WebMvcLinkBuilder
				.linkTo(CozinhaController.class)
				.withRel("cozinhas")
		);
		
		return cozinhaDTO;
	}
	
	public List<CozinhaDTO> toListDTO(List<Cozinha> cozinhas){
		return cozinhas.stream()
				.map(cozinha -> cozinhaDTOConverter(cozinha))
				.collect(Collectors.toList());
	}

	@Override
	public CozinhaDTO toModel(Cozinha entity) {
		return cozinhaDTOConverter(entity);
	}
}
