package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProdutoDTOassembler;
import com.algaworks.algafood.api.disassembler.ProdutoVOdisassembler;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.modelDTO.ProdutoDTO;
import com.algaworks.algafood.domain.model.modelVO.ProdutoVO;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoDTOassembler produtoDTOAssembler;
	
	@Autowired
	private ProdutoVOdisassembler produtoVOdisassembler;
	
	@GetMapping
	public List<ProdutoDTO> findAll(@PathVariable Long restauranteId){
		return produtoDTOAssembler.toListDTO(restauranteService.findById(restauranteId).getProdutos());
	}
	
	
	@GetMapping("/{produtoId}")
	public ProdutoDTO findById(@PathVariable Long produtoId, @PathVariable Long restauranteId) {
		return produtoDTOAssembler.produtoDTOConverter(
				produtoService.findProdutoByIdAndRestaurante(produtoId, restauranteId)
				);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO save(@RequestBody @Valid ProdutoVO produtoVO, @PathVariable Long restauranteId) {
		Produto produto = produtoService.save(produtoVOdisassembler.produtoVOConverter(produtoVO), restauranteId);
		return produtoDTOAssembler.produtoDTOConverter(produto);
	}
//
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ProdutoDTO save(@RequestBody @Valid ProdutoVO produtoVO) {
//		Produto produto = produtoVOdisassembler.produtoVOConverter(produtoVO);
//		try {	
//			return produtoDTOAssembler.produtoDTOConverter(produtoService.save(produto));
//		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
//			throw new NegocioException(e.getMessage(), e);
//		}
//	}
//	
//	@PutMapping("/{id}")
//	public ProdutoDTO update(
//				@PathVariable Long id, 
//				@RequestBody @Valid ProdutoVO produtoVO) {
//		
//			Produto produtoAtual = restauranteService.findById(id);
//			
//			restauranteVOdisassembler.copyToDomainObj(produtoVO, produtoAtual);
//			try {
//				return restauranteDTOAssembler
//						.restauranteDTOConverter(
//								restauranteService.save(restauranteAtual));
//			} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
//				throw new NegocioException(e.getMessage(), e);
//			}
//	}
//	
//	@DeleteMapping("/{id}")
//	public void deleteById(@PathVariable Long id) {
//			findById(id);
//			restauranteService.deleteById(id);	
//	}
//	
//	@PatchMapping("/{id}")
//	public RestauranteDTO updatePartially(
//				@PathVariable Long id,
//				@RequestBody Map<String, Object> campos,
//				HttpServletRequest request){		
//		
//			Restaurante restaurante = restauranteService.findById(id);
//			return restauranteDTOAssembler.restauranteDTOConverter(
//					restauranteService.updatePartially(restaurante, campos, request));			
//	}
//	
//	@PutMapping("/{id}/ativacao")
//	public void ativacao(@PathVariable Long id) {
//		restauranteService.ativacao(id);
//	}
//	
//	@DeleteMapping("/{id}/inativacao")
//	public void inativacao(@PathVariable Long id) {
//		restauranteService.inativacao(id);
//	}
}
