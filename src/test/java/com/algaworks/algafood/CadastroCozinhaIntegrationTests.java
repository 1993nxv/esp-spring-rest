package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.domain.model.Cozinha;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {
	
	@Autowired
	CozinhaController cozinhaController;
	
	@Test
	public void cadastroCozinhaTeste() {

//		Cenário
		Cozinha novaCozinha = Cozinha.builder()
	    	.nome("Tailandesa - Teste")
			.build();
		
//		Ação
	    novaCozinha = cozinhaController.save(novaCozinha);
		
//		Validação
	    assertThat(novaCozinha).isNotNull();
	    assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void cadastroCozinhaSemNomeTeste() {
		
//		Cenário
		Cozinha novaCozinha = Cozinha.builder()
	    	.nome("")
			.build();
		
//		Ação
	    novaCozinha = cozinhaController.save(novaCozinha);
		
//		Validação na anotação
	}
}
