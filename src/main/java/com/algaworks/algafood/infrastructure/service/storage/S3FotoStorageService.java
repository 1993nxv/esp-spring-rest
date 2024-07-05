package com.algaworks.algafood.infrastructure.service.storage;

import java.io.InputStream;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.service.FotoStorageService;

@Service
public class S3FotoStorageService implements FotoStorageService {

	@Override
	public void armazenar(NovaFoto novaFoto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(FotoProduto foto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream recuperar(Path localArquivo) {
		// TODO Auto-generated method stub
		return null;
	}

}
