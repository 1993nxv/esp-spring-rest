package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.modelDTO.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.modelVO.FotoProdutoVO;
import com.algaworks.algafood.domain.service.FotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private DTOAssembler<FotoProduto, FotoProdutoDTO> assemblerDTO;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId,
			@Valid FotoProdutoVO fotoProdutoVO) throws IOException {
		
		MultipartFile file = fotoProdutoVO.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setContentType(file.getContentType());
		foto.setDescricao(fotoProdutoVO.getDescricao());
		foto.setId(produtoId);
		foto.setProduto(produtoService.findProdutoByIdAndRestaurante(produtoId, restauranteId));
		foto.setNomeArquivo(file.getOriginalFilename());
		foto.setTamanho(file.getSize());
		
		return assemblerDTO.toDTO(fotoProdutoService.save(foto, file.getInputStream()), FotoProdutoDTO.class);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO findFotoById(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId
			) {
		return assemblerDTO.toDTO(fotoProdutoService.findFotoById(restauranteId, produtoId), FotoProdutoDTO.class);
	}
	
	@GetMapping
	public ResponseEntity<InputStreamResource> servirFoto(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId,
			@RequestHeader(name = "accept") String acceptHeader
			) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = fotoProdutoService.findFotoById(restauranteId, produtoId);
			InputStreamResource foto = new InputStreamResource(fotoProdutoService.servirFoto(restauranteId, produtoId));
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> acceptMediaTypes = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(mediaTypeFoto, acceptMediaTypes);
			
			return ResponseEntity
					.ok()
					.contentType(mediaTypeFoto)
					.body(foto);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		fotoProdutoService.delete(restauranteId, produtoId);
		return ResponseEntity.ok().build();
	}

	private void verificarCompatibilidadeMediaType(MediaType mediaType, List<MediaType> acceptMediaTypes) throws HttpMediaTypeNotAcceptableException {
		boolean iscompativel = acceptMediaTypes.stream()
				.anyMatch(acceptedMediaType -> acceptedMediaType.isCompatibleWith(mediaType)
		);
		
		if(!iscompativel) {
			throw new HttpMediaTypeNotAcceptableException(acceptMediaTypes);
		}
	}
	
}
