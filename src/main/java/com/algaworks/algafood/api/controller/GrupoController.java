package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelDTO.GrupoDTO;
import com.algaworks.algafood.domain.model.modelVO.GrupoVO;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoService GrupoService;

	@Autowired
	private GrupoDTOassembler GrupoDTOassembler;

	@Autowired
	GrupoVOdisassembler GrupoVOdisassembler;

	@GetMapping
	public List<GrupoDTO> findAll() {
		return GrupoDTOassembler.toListDTO(GrupoService.findAll());
	}

	@GetMapping("/{id}")
	public GrupoDTO findById(@PathVariable Long id) {
		return GrupoDTOassembler.GrupoDTOConverter(GrupoService.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO save(@RequestBody @Valid GrupoVO GrupoVO) {
		try {
			return GrupoDTOassembler
					.GrupoDTOConverter(GrupoService.save(GrupoVOdisassembler.GrupoVOConverter(GrupoVO)));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public GrupoDTO update(@PathVariable Long id, @RequestBody @Valid GrupoVO GrupoVO) {
		Grupo GrupoAtual = GrupoService.findById(id);
		GrupoVOdisassembler.copyToDomainObj(GrupoVO, GrupoAtual);
		try {
			return GrupoDTOassembler.GrupoDTOConverter(GrupoService.save(GrupoAtual));
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PatchMapping("/{id}")
	public GrupoDTO updatePartially(@PathVariable Long id, @RequestBody GrupoVO GrupoVO) {
		Grupo Grupo = GrupoVOdisassembler.GrupoVOConverter(GrupoVO);
		return GrupoDTOassembler.GrupoDTOConverter(GrupoService.updatePartially(id, Grupo));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		GrupoService.deleteById(id);
	}

}
