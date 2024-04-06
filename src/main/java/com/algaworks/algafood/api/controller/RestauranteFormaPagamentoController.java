package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagementoDTOassembler;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{id}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagementoDTOassembler DTOassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> findAll(@PathVariable Long id){
		Restaurante restaurante = restauranteService.findById(id);
		return DTOassembler.toListDTO(restaurante.getFormasPagamento());
	}
		
}
