package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.FotoStorageService;

@Service
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
	public InputStream recuperar(Path localArquivo) {
		try {
			return Files.newInputStream(localArquivo);
		} catch (Exception e) {
			throw new StorageException("Não foi possivel recuperar o arquivo", e.getCause());
		}
	}
	
	public Path getArquivoPath(String nomeArquivo) {
		Path diretorioFotos = storageProperties.getLocal().getDiretorioFotos();
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

}
