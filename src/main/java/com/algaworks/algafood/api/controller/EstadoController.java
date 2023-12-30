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
	public ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			Estado estado = estadoService.findById(id);
			return ResponseEntity.ok(estado);		
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());	
		}
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Estado estado) {
		try {			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(estadoService.save(estado));				
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());			
		}		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Estado estado) {
		try {			
			Estado estadoAtual = estadoService.findById(id);
			BeanUtils.copyProperties(estado, estadoAtual, "id");			
			return ResponseEntity
					.ok(estadoService.save(estadoAtual));			
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> deleteById(@PathVariable Long id){
		try {			
			estadoService.deleteById(id);
			return ResponseEntity.noContent().build();	
		} catch (EntidadeNaoEncontradaException e) {		
			return ResponseEntity.notFound().build();	
		} catch (EntidadeEmUsoException e) {		
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
