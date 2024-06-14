package com.algaworks.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.modelVO.FotoProdutoVO;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId,
			@Valid FotoProdutoVO fotoProdutoVO) {
		
		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoVO.getArquivo().getOriginalFilename();
		
		var arquivoFoto = Path.of("/Users/Delmondes/Desktop/catalogo", nomeArquivo);
		
		System.out.println(arquivoFoto);
		System.out.println(fotoProdutoVO.getArquivo().getContentType());
		System.out.println(fotoProdutoVO.getDescricao());
		
		try {
			fotoProdutoVO.getArquivo().transferTo(arquivoFoto);
			System.out.println("Arquivo salvo com sucesso!");
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
