package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

import com.algaworks.algafood.domain.model.FotoProduto;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	void excluir(FotoProduto foto);
	InputStream recuperar(Path localArquivo);
	
	default String gerarNovoNomeArquivo(String nomeOriginal, int tamanhoUUID) {
		String novoNome = UUID.randomUUID()
				.toString()
				.replace("-", "")
				.substring(0, tamanhoUUID) 
				+ "_" + nomeOriginal;
		return novoNome;
	}
	
	@Builder
	@Getter
	class NovaFoto {
		
		private String nomeArquivo;
		private InputStream inputStream;
		
	}
}
