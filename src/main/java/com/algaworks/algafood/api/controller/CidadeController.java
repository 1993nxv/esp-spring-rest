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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;



@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public List<Cidade> findAll(){
		return cidadeService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {
			
			Cidade cidade = cidadeService.findById(id);
			return ResponseEntity.ok(cidade);
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());
			
		}
				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade save(@RequestBody Cidade cidade){
		return cidadeService.save(cidade);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cidade cidade){
		
		try {
			
			Cidade cidadeAtual = cidadeService.findById(id);
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			cidade = cidadeAtual;
			cidadeService.save(cidade);
			return ResponseEntity.ok(cidade);
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());
			
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cidade> deleteById(@PathVariable Long id){
		try {
			
			cidadeService.deleteById(id);
			return ResponseEntity.noContent().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.notFound().build();
		
		}
	}
}
