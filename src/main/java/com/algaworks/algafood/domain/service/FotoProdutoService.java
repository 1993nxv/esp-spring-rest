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
			fotoStorageService.excluir(foto);
			System.out.println("teste");
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
		
		fotoStorageService.armazenar(novaFoto);
		
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
		return fotoStorageService.recuperar(nomeArquivo);
	}
	
	@Transactional
	public void delete(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = findFotoById(restauranteId, produtoId);
		produtoRepository.deleteFotoById(fotoProduto.getId());
		produtoRepository.flush();
		fotoStorageService.excluir(fotoProduto);
	}
	
}
