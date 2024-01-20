package com.algaworks.algafood.api.exceptionhendler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(CidadeNaoEncontradoException.class)
	public ResponseEntity<?> trataCidadeNaoEncontrada(CidadeNaoEncontradoException e){
		
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.menssagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(problema);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> trataNegocioException(NegocioException e){
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.menssagem(e.getMessage())
				.build();
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(problema);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<?> trataHttpMediaTypeNotSupportedException(){
		Problema problema = Problema.builder()
				.dataHora(LocalDateTime.now())
				.menssagem("O tipo de Content-Type não é aceito.")
				.build();
		
		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
				.body(problema);
	}
}
