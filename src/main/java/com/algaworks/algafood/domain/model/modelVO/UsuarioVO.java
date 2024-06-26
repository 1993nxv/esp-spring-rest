package com.algaworks.algafood.domain.model.modelVO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioVO {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	
}
