package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagementoDTOassembler;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private FormaPagementoDTOassembler DTOassembler;

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public List<FormaPagamentoDTO> findAll(@PathVariable Long restauranteId){
		Restaurante restaurante = restauranteService.findById(restauranteId);
		return DTOassembler.toListDTO(restaurante.getFormasPagamento());
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PostMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.OK)
	public List<FormaPagamentoDTO> adicionaFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		List<FormaPagamento> formaPagamentos = restauranteService.adicionaFormaPagamento(restauranteId, formaPagamentoId);
		return DTOassembler.toListDTO(formaPagamentos);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.removeFormaPagamento(restauranteId, formaPagamentoId);
	}

}
