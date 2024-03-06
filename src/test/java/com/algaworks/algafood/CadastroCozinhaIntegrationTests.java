package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationTests {
	
	@Autowired
	CozinhaService cozinhaService;
	
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaTeste() {
		Cozinha cozinha = Cozinha.builder()
	    	.nome("Tailandesa - Teste")
			.build();
		cozinha = cozinhaService.save(cozinha);
	    assertThat(cozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void deveFalhar_QuandoCadastrarCozinhaSemNomeTeste() {
		Cozinha cozinha = Cozinha.builder()
	    	.nome("")
			.build();
		cozinha = cozinhaService.save(cozinha);
	}
	
	@Test(expected = CozinhaNaoEncontradoException.class)
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		cozinhaService.deleteById(500L);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_QuandoExcluirCozinhaEmUsoTeste() {
		cozinhaService.deleteById(1L);
	}	
}
