package com.algaworks.algafood.api.exceptionhendler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problema {
	
	LocalDateTime dataHora;
	String menssagem;
	
}
