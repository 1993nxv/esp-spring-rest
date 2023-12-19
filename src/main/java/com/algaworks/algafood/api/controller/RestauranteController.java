package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
//	@Autowired
//	RestauranteRepository restauranteRepository;
	
	@GetMapping
	public List<Restaurante> findAll(){
		
				
		//Entendendo o Lazy Loading.
//		System.out.println(restaurantes.get(0).getFormasPagamento());
		
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
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");			
			return ResponseEntity
					.ok(restauranteService.save(restauranteAtual));			
		} catch (EntidadeNaoEncontradaException e) {		
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		try {		
			restauranteService.deleteById(id);
			return ResponseEntity.noContent().build();	
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());		
		}	
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updatePartially(
			@PathVariable Long id,
			@RequestBody Map<String, Object> campos){		
		try {			
			Restaurante restaurante = restauranteService.findById(id);
			Restaurante restauranteSave = restauranteService.updatePartially(restaurante, campos);
			return ResponseEntity.ok(restauranteSave);		
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(e.getMessage());			
		}	
	}
	
	@GetMapping("/por-taxa")
	public List<Restaurante> findByTaxaFreteBetween
		(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){		
		return restauranteService.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/por-nome-e-id")
	public List<Restaurante> porNomeAndCozinhaId(@RequestParam String nome, @RequestParam Long cozinhaId){
		return restauranteService.porNomeAndCozinhaId(nome, cozinhaId);
	}
	
	@GetMapping("/findImp")
	public List<Restaurante> findImpl(
			 String nome, 
			 BigDecimal taxaFreteInicial, 
			 BigDecimal taxaFreteFinal){		
		return restauranteService.findImpl(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/frete-gratis")
	public List<Restaurante> findImpl(String nome){		
		return restauranteService.findFreteGratis(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Restaurante> buscarPrimeiro(){	
		return restauranteService.buscarPrimeiro();	
	}
}
