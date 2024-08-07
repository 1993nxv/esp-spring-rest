package com.algaworks.algafood.api.controller;


import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.algaworks.algafood.api.assembler.FormaPagementoDTOassembler;
import com.algaworks.algafood.api.disassembler.FormaPagamentoVOdisassembler;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.modelVO.FormaPagamentoVO;
import com.algaworks.algafood.domain.service.FormaPagamentoService;



@RestController
@RequestMapping("/formasdepagamento")
public class FormaPagamentoController {
		
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagementoDTOassembler formaPagementoDTOassembler;
	
	@Autowired
	private FormaPagamentoVOdisassembler formaPagamentoVOdisassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDTO>> findAll(ServletWebRequest request){
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest()); // Desabilita Filtro Etag do webconfig
		
		String eTag = "0";
		
		OffsetDateTime dataUltimaAtualizao = formaPagamentoService.getDataUltimaAtualizacao();
		
		if(dataUltimaAtualizao !=  null) {
			eTag = String.valueOf(dataUltimaAtualizao.toEpochSecond());
		}
		
		if (request.checkNotModified(eTag)) {
			return null;
		}
		
		List<FormaPagamentoDTO> formasPagamento = formaPagementoDTOassembler
				.toListDTO(formaPagamentoService.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(20, TimeUnit.SECONDS).cachePublic())
				.eTag(eTag)
				.body(formasPagamento);
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
	
	@PutMapping("/{id}")
	public FormaPagamentoDTO update(@PathVariable Long id, 
				@RequestBody @Valid FormaPagamentoVO formaPagamentoVO){		
			FormaPagamento formaPagamentoAtual = formaPagamentoService.findById(id);
			formaPagamentoVOdisassembler.copyToDomainObj(formaPagamentoVO, formaPagamentoAtual);
			return formaPagementoDTOassembler.formaPagamentoDTOassembler(
					formaPagamentoService.save(formaPagamentoAtual));	
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id){
		formaPagamentoService.findById(id);
		formaPagamentoService.deleteById(id);
	}
}
