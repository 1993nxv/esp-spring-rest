package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;



@RestController
@RequestMapping("/formasdepagamento")
public class FormaPagamentoController {
	
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@GetMapping
	public List<FormaPagamento> findAll(){
		return formaPagamentoService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {
			
			FormaPagamento formaPagamento = formaPagamentoService.findById(id);
			return ResponseEntity.ok(formaPagamento);
			
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());
			
		}
				
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamento save(@RequestBody FormaPagamento formaPagamento){
		return formaPagamentoService.save(formaPagamento);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<FormaPagamento> deleteById(@PathVariable Long id){
		try {
			
			formaPagamentoService.deleteById(id);
			return ResponseEntity.noContent().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeEmUsoException e) {
			
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
