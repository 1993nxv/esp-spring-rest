package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteDTO;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@Autowired
	ProdutoDTOassembler protudoDTOAssembler;
	
	@Autowired
	ProdutoVOdisassembler produtoVOdisassembler;
	
	@GetMapping
	public List<ProdutoDTO> findAll(@PathVariable Long restauranteId){
		return protudoDTOAssembler.toListDTO(restauranteService.findById(restauranteId).getProdutos());
	}
	
	
	@GetMapping("/{id}")
	public ProdutoDTO findById(@PathVariable Long id) {
		return produtoDTOAssembler.restauranteDTOConverter(restauranteService.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO save(@RequestBody @Valid ProdutoVO produtoVO) {
		Produto produto = produtoVOdisassembler.produtoVOConverter(produtoVO);
		try {	
			return produtoDTOAssembler.produtoDTOConverter(produtoService.save(produto));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public ProdutoDTO update(
				@PathVariable Long id, 
				@RequestBody @Valid ProdutoVO produtoVO) {
		
			Produto produtoAtual = restauranteService.findById(id);
			
			restauranteVOdisassembler.copyToDomainObj(produtoVO, produtoAtual);
			try {
				return restauranteDTOAssembler
						.restauranteDTOConverter(
								restauranteService.save(restauranteAtual));
			} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
			findById(id);
			restauranteService.deleteById(id);	
	}
	
	@PatchMapping("/{id}")
	public RestauranteDTO updatePartially(
				@PathVariable Long id,
				@RequestBody Map<String, Object> campos,
				HttpServletRequest request){		
		
			Restaurante restaurante = restauranteService.findById(id);
			return restauranteDTOAssembler.restauranteDTOConverter(
					restauranteService.updatePartially(restaurante, campos, request));			
	}
	
	@PutMapping("/{id}/ativacao")
	public void ativacao(@PathVariable Long id) {
		restauranteService.ativacao(id);
	}
	
	@DeleteMapping("/{id}/inativacao")
	public void inativacao(@PathVariable Long id) {
		restauranteService.inativacao(id);
	}
}
