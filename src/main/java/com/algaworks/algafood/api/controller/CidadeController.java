package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
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

import com.algaworks.algafood.api.assembler.CidadeDTOassembler;
import com.algaworks.algafood.api.disassembler.CidadeVOdisassembler;
import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;
import com.algaworks.algafood.domain.model.modelVO.CidadeVO;
import com.algaworks.algafood.domain.service.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;


@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
@Import(BeanValidatorPluginsConfiguration.class)
public class CidadeController {
		
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDTOassembler cidadeDTOassembler;
	
	@Autowired CidadeVOdisassembler cidadeVOdisassembler;
	
	@ApiOperation("Lista as cidades")
	@GetMapping
	public List<CidadeDTO> findAll(){
		return cidadeDTOassembler.toListDTO(
				cidadeService.findAll());
	}
	
	@ApiOperation("Busca a cidade por id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	@GetMapping("/{id}")
	public CidadeDTO findById(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id){	
		
			return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.findById(id));		
	}
	
	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO save(
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
			@RequestBody @Valid CidadeVO cidadeVO){
		
		try {
			return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.save(
							cidadeVOdisassembler.cidadeVOConverter(cidadeVO)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza uma cidade por id")
	@PutMapping("/{id}")
	public CidadeDTO update(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
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
	
	@ApiOperation("Atualiza uma cidade parcialmente por id")
	@PatchMapping("/{id}")
	public CidadeDTO updatePartially(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
			@RequestBody CidadeVO cidadeVO){
		
		Cidade cidade = cidadeVOdisassembler.cidadeVOConverter(cidadeVO);	
		return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.updatePartially(id, cidade));			
	}
	
	@ApiOperation("Exclui uma cidade por id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id){		
		
			cidadeService.deleteById(id);
	}
	
}
