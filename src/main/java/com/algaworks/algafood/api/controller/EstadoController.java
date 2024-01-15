package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	EstadoService estadoService;
	
	@GetMapping
	public List<Estado> findAll(){
		return estadoService.findAll();
	}
	
	@GetMapping("/{id}")
	public Estado findById(@PathVariable Long id) {
		return estadoService.findById(id);	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado save(@RequestBody Estado estado) {
		return estadoService.save(estado);						
	}
	
	@PutMapping("/{id}")
	public Estado update(@PathVariable Long id, @RequestBody Estado estado) {	
		Estado estadoAtual = estadoService.findById(id);
		BeanUtils.copyProperties(estado, estadoAtual, "id");			
		return estadoService.save(estadoAtual);			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){
		estadoService.deleteById(id);
	}
}
