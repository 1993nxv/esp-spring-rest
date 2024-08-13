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

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;
import com.algaworks.algafood.domain.model.modelVO.CozinhaVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Cozinhas")
@Import(BeanValidatorPluginsConfiguration.class)
public interface CozinhaControllerOpenApi {
	
	@ApiOperation("Lista as cozinhas com paginação")
	public ResponseEntity<Page<CozinhaDTO> > findAll(@PageableDefault(size = 2) Pageable pageable);
	
	@ApiOperation("Busca uma cozinha por ID")
	public ResponseEntity<CozinhaDTO> findById(@PathVariable Long id);
	
	@ApiOperation("Cadastra uma cozinha")
	public CozinhaDTO save(
			@RequestBody 
			@Valid
			CozinhaVO cozinhaVO);
	
	@ApiOperation("Atualiza uma cozinha por ID")
	public CozinhaDTO update(@PathVariable Long id, 
				@RequestBody @Valid CozinhaVO cozinhaVO);
	
	@ApiOperation("Exclui uma cozinha por ID")
	public void deleteById(@PathVariable Long id);
	
	@ApiOperation("Busca uma cozinha por nome")
	public List<Cozinha> findByNome(@RequestParam("nome") String nome);
	
	@ApiOperation("Busca uma cozinha por parte do nome")
	public List<Cozinha> findByNomeContaining(@RequestParam("nome") String nome, Pageable pageable);
	
	@ApiOperation("Busca a primeira cozinha")
	public Optional<Cozinha> buscarPrimeiro();
}
