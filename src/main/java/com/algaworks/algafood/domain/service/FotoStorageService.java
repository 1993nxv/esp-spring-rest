package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import com.algaworks.algafood.domain.model.FotoProduto;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	void armazenar(NovaFoto novaFoto);
	void excluir(FotoProduto foto);
	FotoRecuperada recuperar(String nomeArquivo);
	
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
	public class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream;
		private String contentType;
		private Long tamanho;
	}
	
	@Builder
	@Getter
	public class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return this.url != null;
		}
		
		public boolean temImputStream() {
			return this.inputStream != null;
		}
	}
}
