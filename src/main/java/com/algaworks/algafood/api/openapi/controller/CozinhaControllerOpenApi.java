package com.algaworks.algafood.api.openapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;
import com.algaworks.algafood.domain.model.modelVO.CozinhaVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Cozinhas")
@Import(BeanValidatorPluginsConfiguration.class)
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista as cozinhas com paginação")
	public ResponseEntity<Page<CozinhaDTO> > findAll(@PageableDefault(size = 2) Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	public ResponseEntity<CozinhaDTO> findById(
			@ApiParam(value = "ID de uma cozinha", example = "1") 
			@PathVariable Long id);
	
	@ApiOperation("Cadastra uma cozinha")
	public CozinhaDTO save(
			@ApiParam(name = "corpo", value = "Representação de uma cozinha")
			@RequestBody 
			@Valid
			CozinhaVO cozinhaVO);
	
	@ApiOperation("Atualiza uma cozinha por ID")
	public CozinhaDTO update(
			@ApiParam(value = "ID de uma cozinha", example = "1") 
			@PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de uma cozinha")
			@RequestBody @Valid CozinhaVO cozinhaVO);
	
	@ApiOperation("Exclui uma cozinha por ID")
	public void deleteById(
			@ApiParam(value = "ID de uma cozinha", example = "1") 
			@PathVariable Long id);
	
	@ApiOperation("Busca uma cozinha por nome")
	public List<Cozinha> findByNome(
			@ApiParam(value = "Nome de uma cozinha", example = "Brasileira") 
			@RequestParam("nome") String nome);
	
	@ApiOperation("Busca uma cozinha por parte do nome com paginação")
	public List<Cozinha> findByNomeContaining(
			@ApiParam(value = "Parte do nome de uma cozinha", example = "Brasi") 
			@RequestParam("nome") String nome, Pageable pageable);
	
	@ApiOperation("Busca a primeira cozinha")
	public Optional<Cozinha> buscarPrimeiro();
}
