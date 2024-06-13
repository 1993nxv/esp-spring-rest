package com.algaworks.algafood.domain.model.modelVO;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoVO {
	
	private MultipartFile arquivo;
	private String descricao;
	
}
