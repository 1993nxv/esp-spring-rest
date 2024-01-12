package com.algaworks.algafood.api.controller;


import java.util.List;
import java.util.Optional;

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
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
		try {			
			Cozinha cozinha = cozinhaService.findById(id);
			return ResponseEntity.ok(cozinha);			
		} catch (EntidadeNaoEncontradaException e) {		
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());	
		}			
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha save(@RequestBody Cozinha cozinha){
		return cozinhaService.save(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cozinha cozinha){		
		try {			
			Cozinha cozinhaAtual = cozinhaService.findById(id);
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cozinha = cozinhaAtual;
			cozinhaService.save(cozinha);
			return ResponseEntity.ok(cozinha);
			
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());		
		}	
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Cozinha> deleteById(@PathVariable Long id){
//		try {			
//			cozinhaService.deleteById(id);
//			return ResponseEntity.noContent().build();	
//		} catch (EntidadeNaoEncontradaException e) {		
//			return ResponseEntity.notFound().build();	
//		} catch (EntidadeEmUsoException e) {		
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){
		try	{
			cozinhaService.deleteById(id);
		} catch (EntidadeNaoEncontradaException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
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
