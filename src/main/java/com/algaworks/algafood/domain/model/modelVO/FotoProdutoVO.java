package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoVO {
	
	@NotNull
	@FileSize(max = "500KB")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
	
}
