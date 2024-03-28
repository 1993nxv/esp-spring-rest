package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagementoDTOassembler;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.service.FormaPagamentoService;



@RestController
@RequestMapping("/formasdepagamento")
public class FormaPagamentoController {
		
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagementoDTOassembler formaPagementoDTOassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> findAll(){
		return formaPagementoDTOassembler
				.toListDTO(formaPagamentoService.findAll());
	}
	
	@GetMapping("/{id}")
	public FormaPagamentoDTO findById(@PathVariable Long id){		
			FormaPagamento formaPagamento = formaPagamentoService.findById(id);
			return formaPagementoDTOassembler.formaPagamentoDTOassembler(formaPagamento);					
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO save(@RequestBody FormaPagamento formaPagamento){
		return formaPagementoDTOassembler
				.formaPagamentoDTOassembler(
						formaPagamentoService.save(formaPagamento));
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id){
		formaPagamentoService.findById(id);
		formaPagamentoService.deleteById(id);
	}
}
