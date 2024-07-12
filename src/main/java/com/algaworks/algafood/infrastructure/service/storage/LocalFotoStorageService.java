package com.algaworks.algafood.infrastructure.service.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.FotoStorageService;


public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;
	
	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possivel armazenar o arquivo", e.getCause());
		}
	}
	
	@Override
	public void excluir(FotoProduto foto) {
		try {
			Path arquivoPath = getArquivoPath(foto.getNomeArquivo());
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel excluir o arquivo", e.getCause());
		}
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		try {
			FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
					.inputStream(Files.newInputStream(getArquivoPath(nomeArquivo)))
					.build();
			return fotoRecuperada;
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar o arquivo", e.getCause());
		}
	}
	
	public Path getArquivoPath(String nomeArquivo) {
		Path diretorioFotos = storageProperties.getLocal().getDiretorioFotos();
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

}
