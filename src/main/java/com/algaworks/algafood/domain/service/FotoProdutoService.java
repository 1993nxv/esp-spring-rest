package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import com.algaworks.algafood.infrastructure.service.storage.StorageException;

@Service
public class FotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto save(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestaurnateId();
		Long produtoId = foto.getProdutoId();
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(foto);
			fotoStorageService.excluir(fotoExistente.get());
		}
		
		foto.setNomeArquivo(fotoStorageService.gerarNovoNomeArquivo(foto.getNomeArquivo(), 16));
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.build();
		
		fotoStorageService.armazenar(novaFoto);
		
		return foto;
	}
	
	public FotoProduto findFotoById(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new StorageException("Não existe uma foto para o produto de id:" + produtoId));
	}
	
}
