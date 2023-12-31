package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.PermissaoService;



@RestController
@RequestMapping("/permissoes")
public class PermissaoController {
		
	@Autowired
	private PermissaoService permissaoService;
	
	@GetMapping
	public List<Permissao> findAll(){
		return permissaoService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {			
			Permissao permissao = permissaoService.findById(id);
			return ResponseEntity.ok(permissao);			
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());			
		}			
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Permissao save(@RequestBody Permissao permissao){
		return permissaoService.save(permissao);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Permissao> deleteById(@PathVariable Long id){
		try {			
			permissaoService.deleteById(id);
			return ResponseEntity.noContent().build();		
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity.notFound().build();	
		} catch (EntidadeEmUsoException e) {		
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
