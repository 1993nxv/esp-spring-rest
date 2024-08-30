package com.algaworks.algafood.api.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointControler {
	
	@GetMapping
	public RootEntryPointDTO root() {
		var rootEntryPointDTO = new RootEntryPointDTO();
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(CozinhaController.class).withRel("cozinhas"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(RestauranteController.class).withRel("restaurantes"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(GrupoController.class).withRel("grupos"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withRel("usuarios"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(FormaPagamentoController.class).withRel("formas-pagamento"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withRel("cidades"));
		rootEntryPointDTO.add(WebMvcLinkBuilder.linkTo(EstadoController.class).withRel("estados"));
		
		return rootEntryPointDTO;
	}
	
	private static class RootEntryPointDTO extends RepresentationModel<RootEntryPointDTO>{}
}
