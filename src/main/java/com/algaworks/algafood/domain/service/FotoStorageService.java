package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import com.algaworks.algafood.domain.model.FotoProduto;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	void excluir(FotoProduto foto);
	
	default String gerarNovoNomeArquivo(String nomeOriginal) {
		return UUID.randomUUID().toString() + "_" + nomeOriginal;
	}
	
	@Builder
	@Getter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
}
