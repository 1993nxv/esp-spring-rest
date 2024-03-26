package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoDTOassembler;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.modelDTO.EstadoDTO;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	EstadoService estadoService;
	
	@Autowired
	EstadoDTOassembler estadoDTOassembler;
	
	@GetMapping
	public List<EstadoDTO> findAll(){
		return estadoDTOassembler.toListDTO(estadoService.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoDTO findById(@PathVariable Long id) {
		return estadoDTOassembler.estadoDTOConverter(estadoService.findById(id));	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO save(@RequestBody @Valid Estado estado) {
		return estadoDTOassembler.estadoDTOConverter(estadoService.save(estado));						
	}
	
	@PutMapping("/{id}")
	public EstadoDTO update(@PathVariable Long id, @RequestBody @Valid Estado estado) {	
		Estado estadoAtual = estadoService.findById(id);
		BeanUtils.copyProperties(estado, estadoAtual, "id");			
		return estadoDTOassembler.estadoDTOConverter(estadoService.save(estadoAtual));		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){
		estadoService.deleteById(id);
	}
}
