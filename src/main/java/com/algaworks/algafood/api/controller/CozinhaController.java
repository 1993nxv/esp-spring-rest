package com.algaworks.algafood.api.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CozinhaDTOassembler;
import com.algaworks.algafood.api.disassembler.VODisassembler;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;
import com.algaworks.algafood.domain.model.modelVO.CozinhaVO;
import com.algaworks.algafood.domain.service.CozinhaService;



@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
		
	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaDTOassembler cozinhaDTOassembler;
	
	@Autowired
	private VODisassembler<CozinhaVO, Cozinha> cozinhaVOdisassembler;
	
	@GetMapping
	public List<CozinhaDTO> findAll(){
		return cozinhaDTOassembler.toListDTO(cozinhaService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CozinhaDTO> findById(@PathVariable Long id){		
			CozinhaDTO cozinhaDTO = cozinhaDTOassembler
					.cozinhaDTOConverter(cozinhaService.findById(id));
			return ResponseEntity.ok(cozinhaDTO);					
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO save(
			@RequestBody 
			@Valid
			CozinhaVO cozinhaVO){
		Cozinha cozinha = cozinhaVOdisassembler.toEntity(cozinhaVO, Cozinha.class);
		return cozinhaDTOassembler.cozinhaDTOConverter(cozinhaService.save(cozinha));
	}
	
	@PutMapping("/{id}")
	public CozinhaDTO update(@PathVariable Long id, 
				@RequestBody @Valid CozinhaVO cozinhaVO){		
			Cozinha cozinhaAtual = cozinhaService.findById(id);
			cozinhaVOdisassembler.copyToEntity(cozinhaVO, cozinhaAtual);
			return cozinhaDTOassembler.cozinhaDTOConverter(cozinhaService.save(cozinhaAtual));	
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){
		cozinhaService.deleteById(id);
	}
	
	@GetMapping("/nome")
	public List<Cozinha> findByNome(@RequestParam("nome") String nome) {
		return cozinhaService.findByNome(nome);
	}
	
	@GetMapping("/nomelike")
	public List<Cozinha> findByNomeContaining(@RequestParam("nome") String nome) {
		return cozinhaService.findByNomeContaining(nome);
	}
	
	@GetMapping("/primeiro")
	public Optional<Cozinha> buscarPrimeiro(){		
		return cozinhaService.buscarPrimeiro();	
	}
}
