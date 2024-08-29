package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelDTO.UsuarioDTO;

@Component
public class UsuarioDTOassembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTOassembler() {
		super(UsuarioController.class,UsuarioDTO.class);
	}
	
	public UsuarioDTO usuarioDTOConverter(Usuario usuario) {
		UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
		
		usuarioDTO.add(WebMvcLinkBuilder.linkTo(UsuarioController.class)
				.slash(usuarioDTO.getId()).withSelfRel());
		
		usuarioDTO.add(WebMvcLinkBuilder.linkTo(UsuarioController.class)
				.withRel("usuarios"));
		
		usuarioDTO.add(WebMvcLinkBuilder.linkTo(UsuarioController.class)
				.slash(usuarioDTO.getId()).slash("grupos").withRel("grupos-usuario"));
		
		return usuarioDTO;
	}
	
	@Override
	public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(UsuarioController.class)
				.withSelfRel());
	}
	
	@Override
	public UsuarioDTO toModel(Usuario usuario) {
		return usuarioDTOConverter(usuario);
	}
}
