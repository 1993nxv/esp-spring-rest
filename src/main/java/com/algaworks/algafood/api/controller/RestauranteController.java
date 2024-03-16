package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteDTO;
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
	public RestauranteDTO findById(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.findById(id);
		RestauranteDTO restauranteDTO = new RestauranteDTO();
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		restauranteDTO.setId(restaurante.getId());
		restauranteDTO.setNome(restaurante.getNome());
		restauranteDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restauranteDTO.setCozinha(cozinhaDTO);
		
		return restauranteDTO;	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante save(@RequestBody @Valid Restaurante restaurante) {
		try {	
			return restauranteService.save(restaurante);
		} catch (CozinhaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Restaurante update(
				@PathVariable Long id, 
				@RequestBody @Valid Restaurante restaurante) {
		
			Restaurante restauranteAtual = restauranteService.findById(id);
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro");			
			try {
				return restauranteService.save(restauranteAtual);
			} catch (CozinhaNaoEncontradoException e) {
				throw new NegocioException(e.getMessage(), e);
			}
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
			findById(id);
			restauranteService.deleteById(id);	
	}
	
	@PatchMapping("/{id}")
	public Restaurante updatePartially(
				@PathVariable Long id,
				@RequestBody Map<String, Object> campos,
				HttpServletRequest request){		
		
			Restaurante restaurante = restauranteService.findById(id);
			return restauranteService.updatePartially(restaurante, campos, request);			
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
