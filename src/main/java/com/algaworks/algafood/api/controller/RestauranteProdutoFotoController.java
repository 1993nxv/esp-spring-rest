package com.algaworks.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.DTOAssembler;
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
}
