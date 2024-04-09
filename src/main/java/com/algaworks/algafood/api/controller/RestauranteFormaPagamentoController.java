package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagementoDTOassembler;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagementoDTOassembler DTOassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> findAll(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.findById(restauranteId);
		return DTOassembler.toListDTO(restaurante.getFormasPagamento());
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.removeFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@PostMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.OK)
	public List<FormaPagamentoDTO> adicionaFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		List<FormaPagamento> formaPagamentos = restauranteService.adicionaFormaPagamento(restauranteId, formaPagamentoId);
		return DTOassembler.toListDTO(formaPagamentos);
	}
}
