package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;

@Component
public class CidadeDTOassembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTOassembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	public CidadeDTO cidadeDTOConverter(Cidade cidade) {
		CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);
		
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.slash(cidadeDTO.getEstado().getId()).withSelfRel());
		cidadeDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.withRel("cidades"));
		cidadeDTO.getEstado().add(WebMvcLinkBuilder.linkTo(EstadoController.class)
				.slash(cidadeDTO.getEstado().getId()).withSelfRel());
		return cidadeDTO;
	}
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(CidadeController.class)
				.withSelfRel());
	}
	
	@Override
	public CidadeDTO toModel(Cidade cidade) {
		return cidadeDTOConverter(cidade);
	}
}
