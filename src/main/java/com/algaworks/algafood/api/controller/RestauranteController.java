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

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> findAll(){
		return restauranteService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		try {
			
			Restaurante restaurante = restauranteService.findById(id);
			return ResponseEntity.ok(restaurante);
		
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		
		}
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Restaurante restaurante) {
		try {
			
			return ResponseEntity
					.status(HttpStatus.CREATED)
					.body(restauranteService.save(restaurante));
		
		
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());
			
		}
			
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		try {
			
			Restaurante restauranteAtual = restauranteService.findById(id);
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
			
			return ResponseEntity
					.ok(restauranteService.save(restauranteAtual));
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Restaurante> deleteById(@PathVariable Long id) {
		
		restauranteService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
}
