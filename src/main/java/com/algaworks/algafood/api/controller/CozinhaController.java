package com.algaworks.algafood.api.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository; 
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<Cozinha> findAll(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> findById(@PathVariable Long id){
		
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

//		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
	
		if(!cozinha.isEmpty()) {
			return ResponseEntity.ok(cozinha.get());
		} else {
			return ResponseEntity.noContent().build();	
		}
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha save(@RequestBody Cozinha cozinha){
		return cozinhaService.save(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> update(@PathVariable Long id, @RequestBody Cozinha cozinha){
		
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);
		
		if(!cozinhaAtual.isEmpty()) {
		
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
//			cozinhaAtual.get().setNome(cozinha.getNome());
			
			cozinha = cozinhaAtual.get();
			cozinhaRepository.save(cozinha);
			
			return ResponseEntity.ok(cozinha);
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id){
		try {
			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
			
			if(!cozinha.isEmpty()) {
				cozinhaRepository.delete(cozinha.get());
			}
			return ResponseEntity.noContent().build();
		} catch (DataIntegrityViolationException error) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
