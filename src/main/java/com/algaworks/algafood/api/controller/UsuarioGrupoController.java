package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;
import com.algaworks.algafood.domain.service.UsuarioService;



@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
		
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DTOAssembler<Grupo, GrupoDTO, UsuarioGrupoController> assemblerDTO;
	
	@GetMapping
	public List<GrupoDTO> findAll(@PathVariable Long usuarioId){
		return assemblerDTO.toListDTO(
				usuarioService.findById(usuarioId).getGrupos(), 
				GrupoDTO.class);
	}
		
}
