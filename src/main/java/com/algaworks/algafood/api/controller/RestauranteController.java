package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Restaurante> findById(@PathVariable Long id) {
		try {
			
			Restaurante restaurante = restauranteService.findById(id);
			return ResponseEntity.ok(restaurante);
		
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.notFound().build();
		
		}
	}
}
