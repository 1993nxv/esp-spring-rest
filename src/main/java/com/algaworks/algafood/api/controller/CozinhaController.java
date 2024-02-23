package com.algaworks.algafood.api.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;



@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
		
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<Cozinha> findAll(){
		return cozinhaService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){		
			Cozinha cozinha = cozinhaService.findById(id);
			return ResponseEntity.ok(cozinha);						
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha save(
			@RequestBody 
			@Valid 
			Cozinha cozinha){
		return cozinhaService.save(cozinha);
	}
	
	@PutMapping("/{id}")
	public Cozinha update(@PathVariable Long id, 
				@RequestBody @Valid Cozinha cozinha){		
			Cozinha cozinhaAtual = cozinhaService.findById(id);
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			return cozinhaService.save(cozinhaAtual);	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){
		cozinhaService.deleteById(id);
	}
	
	@GetMapping("/nome")
	public List<Cozinha> findByNome(@RequestParam("nome") String nome) {
		return cozinhaService.findByNome(nome);
	}
	
	@GetMapping("/nomelike")
	public List<Cozinha> findByNomeContaining(@RequestParam("nome") String nome) {
		return cozinhaService.findByNomeContaining(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Cozinha> buscarPrimeiro(){		
		return cozinhaService.buscarPrimeiro();	
	}
}
