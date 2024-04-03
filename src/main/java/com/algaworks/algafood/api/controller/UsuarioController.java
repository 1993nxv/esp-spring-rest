package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioDTOassembler;
import com.algaworks.algafood.api.disassembler.UsuarioVOdisassembler;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelDTO.UsuarioDTO;
import com.algaworks.algafood.domain.model.modelVO.UsuarioVO;
import com.algaworks.algafood.domain.service.UsuarioService;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
		
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioDTOassembler usuarioDTOassembler;
	
	@Autowired 
	private UsuarioVOdisassembler usuarioVOdisassembler;
	
	@GetMapping
	public List<UsuarioDTO> findAll(){
		return usuarioDTOassembler.toListDTO(
				usuarioService.findAll());
	}
	
	@GetMapping("/{id}")
	public UsuarioDTO findById(@PathVariable Long id){			
			return usuarioDTOassembler.usuarioDTOConverter(
					usuarioService.findById(id));		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO save(@RequestBody @Valid UsuarioVO usuarioVO){
		try {
			return usuarioDTOassembler.usuarioDTOConverter(
					usuarioService.save(
							usuarioVOdisassembler.usuarioVOConverter(usuarioVO)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public UsuarioDTO update(@PathVariable Long id, @RequestBody @Valid UsuarioVO usuarioVO){		
		Usuario usuarioAtual = usuarioService.findById(id);
		usuarioVOdisassembler.copyToDomainObj(usuarioVO, usuarioAtual);
		try {
			return usuarioDTOassembler.usuarioDTOConverter(
					usuarioService.save(usuarioAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PatchMapping("/{id}")
	public UsuarioDTO updatePartially(@PathVariable Long id, @RequestBody UsuarioVO usuarioVO){						
		Usuario usuario = usuarioVOdisassembler.usuarioVOConverter(usuarioVO);	
		return usuarioDTOassembler.usuarioDTOConverter(
				usuarioService.updatePartially(id, usuario));			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){		
		usuarioService.deleteById(id);
	}
	
}
