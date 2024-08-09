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

import com.algaworks.algafood.api.assembler.GrupoDTOassembler;
import com.algaworks.algafood.api.disassembler.GrupoVOdisassembler;
import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;
import com.algaworks.algafood.domain.model.modelVO.GrupoVO;
import com.algaworks.algafood.domain.service.GrupoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Grupos")
@RestController
@RequestMapping("/grupos")
@Import(BeanValidatorPluginsConfiguration.class)
public class GrupoController {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoDTOassembler grupoDTOassembler;

	@Autowired
	private GrupoVOdisassembler grupoVOdisassembler;
	
	@ApiOperation("Lista os grupos")
	@GetMapping
	public List<GrupoDTO> findAll() {
		return grupoDTOassembler.toListDTO(grupoService.findAll());
	}
	
	@ApiOperation("Busca o grupo por id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "Id do grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	@GetMapping("/{id}")
	public GrupoDTO findById(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id) {
		return grupoDTOassembler.grupoDTOConverter(grupoService.findById(id));
	}
	
	@ApiOperation("Cadastra um grupo")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO save(
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody @Valid GrupoVO grupoVO) {
		try {
			return grupoDTOassembler
					.grupoDTOConverter(grupoService.save(grupoVOdisassembler.grupoVOConverter(grupoVO)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza um grupo por Id")
	@PutMapping("/{id}")
	public GrupoDTO update(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id,
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody @Valid GrupoVO grupoVO) {
		Grupo grupoAtual = grupoService.findById(id);
		grupoVOdisassembler.copyToDomainObj(grupoVO, grupoAtual);
		try {
			return grupoDTOassembler.grupoDTOConverter(grupoService.save(grupoAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@ApiOperation("Atualiza um grupo parcialmente por Id")
	@PatchMapping("/{id}")
	public GrupoDTO updatePartially(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id, 
			@ApiParam(name = "corpo", value = "Representação de um grupo")
			@RequestBody GrupoVO grupoVO) {
		Grupo grupo = grupoVOdisassembler.grupoVOConverter(grupoVO);
		return grupoDTOassembler.grupoDTOConverter(grupoService.updatePartially(id, grupo));
	}
	
	@ApiOperation("Exclui um grupo por Id")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(
			@ApiParam(value = "ID de um grupo", example = "1")
			@PathVariable Long id) {
		grupoService.deleteById(id);
	}

}
