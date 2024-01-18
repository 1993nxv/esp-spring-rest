package com.algaworks.algafood.api.controller;


import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;



@RestController
@RequestMapping("/cidades")
public class CidadeController {
		
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public List<Cidade> findAll(){
		return cidadeService.findAll();
	}
	
	@GetMapping("/{id}")
	public Cidade findById(@PathVariable Long id){			
			return cidadeService.findById(id);		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade save(@RequestBody Cidade cidade){
		try {
			return cidadeService.save(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new EntidadeNaoEncontradaException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Cidade update(@PathVariable Long id, @RequestBody Cidade cidade){		
		Cidade cidadeAtual = cidadeService.findById(id);
		BeanUtils.copyProperties(cidade, cidadeAtual, "id");
		try {
			return cidadeService.save(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new EntidadeNaoEncontradaException(e.getMessage(), e);
		}
	}
	
	@PatchMapping("/{id}")
	public Cidade updatePartially(@PathVariable Long id, @RequestBody Cidade cidade){						
			return cidadeService.updatePartially(id, cidade);			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){		
			cidadeService.deleteById(id);
	}
}
