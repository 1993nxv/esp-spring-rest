package com.algaworks.algafood.api.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.model.modelDTO.PermissaoDTO;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

	@Autowired
	private GrupoService grupoService;
	
	private ModelMapper modelMapper = new ModelMapper();

	private DTOAssembler<Permissao, PermissaoDTO, GrupoPermissaoController> assemblerDTO = 
		new DTOAssembler<>(PermissaoDTO.class, GrupoPermissaoController.class, modelMapper);
	
	@GetMapping
	public List<PermissaoDTO> findPermissoes(@PathVariable Long grupoId) {
		return assemblerDTO.toListDTO(grupoService.findPermissoes(grupoId), PermissaoDTO.class);
	}
	
	@PutMapping("/{permissaoId}")
	public List<PermissaoDTO> adicionaPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		return assemblerDTO.toListDTO(grupoService.adicionaPermissao(grupoId, permissaoId), PermissaoDTO.class);
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removePermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.removePermissao(grupoId, permissaoId);
	} 
}
