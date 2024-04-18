package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.domain.model.modelDTO.PermissaoDTO;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{id}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private DTOAssembler permissaoDTOAssembler;

	@GetMapping
	public List<PermissaoDTO> findPermissoes(@PathVariable Long id) {
		return permissaoDTOAssembler.toListDTO(grupoService.findPermissoes(id), PermissaoDTO.class) ;
	}

}
