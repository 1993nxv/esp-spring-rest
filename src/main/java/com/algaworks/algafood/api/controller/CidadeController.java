package com.algaworks.algafood.api.controller;


import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algafood.api.assembler.CidadeDTOassembler;
import com.algaworks.algafood.api.disassembler.CidadeVOdisassembler;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;
import com.algaworks.algafood.domain.model.modelVO.CidadeVO;
import com.algaworks.algafood.domain.service.CidadeService;


@RestController
@RequestMapping(path = "/cidades", produces = MediaType.APPLICATION_JSON_VALUE )
public class CidadeController implements CidadeControllerOpenApi {
		
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDTOassembler cidadeDTOassembler;
	
	@Autowired CidadeVOdisassembler cidadeVOdisassembler;
	
	@GetMapping
	public List<CidadeDTO> findAll(){
		return cidadeDTOassembler.toListDTO(
				cidadeService.findAll());
	}
	
	@GetMapping("/{id}")
	public CidadeDTO findById(@PathVariable Long id){	
		
			return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.findById(id));		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO save(@RequestBody @Valid CidadeVO cidadeVO){
		try {
			CidadeDTO cidadeDTO = cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.save(cidadeVOdisassembler.cidadeVOConverter(cidadeVO)));
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}")
					.buildAndExpand(cidadeDTO.getId())
					.toUri();
			
			HttpServletResponse response = ((ServletRequestAttributes) 
					RequestContextHolder.getRequestAttributes())
					.getResponse();
			
			response.setHeader(HttpHeaders.LOCATION, uri.toString());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public CidadeDTO update(
			@PathVariable Long id, 
			@RequestBody @Valid CidadeVO cidadeVO){	
		
		Cidade cidadeAtual = cidadeService.findById(id);
		cidadeVOdisassembler.copyToDomainObj(cidadeVO, cidadeAtual);
		try {
			return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.save(cidadeAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PatchMapping("/{id}")
	public CidadeDTO updatePartially(
			@PathVariable Long id, 
			@RequestBody CidadeVO cidadeVO){
		
		Cidade cidade = cidadeVOdisassembler.cidadeVOConverter(cidadeVO);	
		return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.updatePartially(id, cidade));			
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id){		
			cidadeService.deleteById(id);
	}
	
}
