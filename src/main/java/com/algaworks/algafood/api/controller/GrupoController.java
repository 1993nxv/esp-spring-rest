package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.algaworks.algafood.api.assembler.GrupoDTOassembler;
import com.algaworks.algafood.api.disassembler.GrupoVOdisassembler;
import com.algaworks.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;
import com.algaworks.algafood.domain.model.modelVO.GrupoVO;
import com.algaworks.algafood.domain.service.GrupoService;


@RestController
@RequestMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private GrupoDTOassembler grupoDTOassembler;

	@Autowired
	private GrupoVOdisassembler grupoVOdisassembler;
	
	@GetMapping
	public List<GrupoDTO> findAll() {
		return grupoDTOassembler.toListDTO(grupoService.findAll());
	}

	@GetMapping("/{id}")
	public GrupoDTO findById(@PathVariable Long id) {
		return grupoDTOassembler.grupoDTOConverter(grupoService.findById(id));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO save(@RequestBody @Valid GrupoVO grupoVO) {
		try {
			return grupoDTOassembler
					.grupoDTOConverter(grupoService.save(grupoVOdisassembler.grupoVOConverter(grupoVO)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public GrupoDTO update(@PathVariable Long id, @RequestBody @Valid GrupoVO grupoVO) {
		Grupo grupoAtual = grupoService.findById(id);
		grupoVOdisassembler.copyToDomainObj(grupoVO, grupoAtual);
		try {
			return grupoDTOassembler.grupoDTOConverter(grupoService.save(grupoAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PatchMapping("/{id}")
	public GrupoDTO updatePartially(@PathVariable Long id, @RequestBody GrupoVO grupoVO) {
		Grupo grupo = grupoVOdisassembler.grupoVOConverter(grupoVO);
		return grupoDTOassembler.grupoDTOConverter(grupoService.updatePartially(id, grupo));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		grupoService.deleteById(id);
	}

}
