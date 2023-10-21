package com.algaworks.algafood.api.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(path = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {
	
	@Autowired
	CozinhaRepository cozinhaRepository; 
	
	@GetMapping
	public List<Cozinha> findAll(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Optional<Cozinha>> findById(@PathVariable Long id){
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		return ResponseEntity.ok().body(cozinha);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id){
		return ResponseEntity.noContent().build();
	}
}
