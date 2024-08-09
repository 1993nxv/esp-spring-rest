package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;
import com.algaworks.algafood.domain.model.modelVO.GrupoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Grupos")
@Import(BeanValidatorPluginsConfiguration.class)
public interface GrupoControllerOpenApi {
	
	@ApiOperation("Lista os grupos")
	public List<GrupoDTO> findAll();
	
	@ApiOperation("Busca o grupo por id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	public GrupoDTO findById(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id);
	
	@ApiOperation("Cadastra um grupo")
	public GrupoDTO save(
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody @Valid GrupoVO grupoVO);
	
	@ApiOperation("Atualiza um grupo por Id")
	public GrupoDTO update(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody @Valid GrupoVO grupoVO);
	
	@ApiOperation("Atualiza um grupo parcialmente por Id")
	public GrupoDTO updatePartially(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody GrupoVO grupoVO);
	
	@ApiOperation("Exclui um grupo por Id")
	public void deleteById(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id);
	
}
