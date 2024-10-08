package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoVO {
	
	
	@NotNull
	@FileSize(max = "600KB")
	@FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	@ApiParam(hidden = true)
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
	
}
