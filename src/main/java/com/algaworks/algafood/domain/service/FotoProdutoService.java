package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.domain.exception.FotoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;
import com.algaworks.algafood.infrastructure.service.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.S3FotoStorageService;

@Service
public class FotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private LocalFotoStorageService fotoStorageService;
	
	@Autowired
	private S3FotoStorageService s3FotoStorageService;
	
	
	@Transactional
	public FotoProduto save(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestaurnateId();
		Long produtoId = foto.getProdutoId();
		
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
		if(fotoExistente.isPresent()) {
			produtoRepository.delete(foto);
			s3FotoStorageService.excluir(foto);
//			fotoStorageService.excluir(fotoExistente.get());
		}
		
		foto.setNomeArquivo(fotoStorageService.gerarNovoNomeArquivo(foto.getNomeArquivo(), 16));
		foto = produtoRepository.save(foto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(foto.getNomeArquivo())
				.inputStream(dadosArquivo)
				.contentType(foto.getContentType())
				.tamanho(foto.getTamanho())
				.build();
		
		s3FotoStorageService.armazenar(novaFoto);
		
		return foto;
	}
	
	public FotoProduto findFotoById(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(() -> new FotoNaoEncontradaException(produtoId));
	}
	
	public FotoRecuperada servirFoto(
			@PathVariable Long restauranteId,
			@PathVariable Long produtoId
			) {
		FotoProduto fotoProduto = findFotoById(restauranteId, produtoId);
		String nomeArquivo = fotoProduto.getNomeArquivo();
		return s3FotoStorageService.recuperar(nomeArquivo);
//		return fotoStorageService.recuperar(nomeArquivo).getInputSream();
	}
	
	@Transactional
	public void delete(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = findFotoById(restauranteId, produtoId);
		produtoRepository.deleteFotoById(fotoProduto.getId());
		produtoRepository.flush();
		s3FotoStorageService.excluir(fotoProduto);
	}
	
}
