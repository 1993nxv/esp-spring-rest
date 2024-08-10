package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;
import com.algaworks.algafood.domain.model.modelVO.CidadeVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Cidades")
@Import(BeanValidatorPluginsConfiguration.class)
public interface CidadeControllerOpenApi {
	
	@ApiOperation("Lista as cidades")
	public List<CidadeDTO> findAll();
	
	@ApiOperation("Busca a cidade por id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id da cidade inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	public CidadeDTO findById(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id);
	
	@ApiOperation("Cadastra uma cidade")
	public CidadeDTO save(
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
			@RequestBody @Valid CidadeVO cidadeVO);
	
	@ApiOperation("Atualiza uma cidade por id")
	public CidadeDTO update(
			@ApiParam(value = "ID de uma cidade", example = "1") 
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
			@RequestBody @Valid CidadeVO cidadeVO);
	
	@ApiOperation("Atualiza uma cidade parcialmente por id")
	public CidadeDTO updatePartially(
			@ApiParam(value = "ID de uma cidade", example = "1")
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma cidade") 
			@RequestBody CidadeVO cidadeVO);
	
	@ApiOperation("Exclui uma cidade por id")
	public void deleteById(
			@ApiParam(value = "ID de uma cidade", example = "1")
			@PathVariable Long id);
}
