package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private static final String PRODUTO_EM_USO = "Produto com id:%d não pode ser removido, pois está em uso.";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<Produto> findAll(){	
		return produtoRepository.findAll();
	}
	
	public Produto findById(Long id){	
			Optional<Produto> produto = produtoRepository.findById(id);			
			return produto
					.orElseThrow(() -> new ProdutoNaoEncontradoException(id));	
	}
	
	@Transactional
	public Produto save(Produto produto){
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void deleteById(Long id) {
		findById(id);	
		try {	
			produtoRepository.deleteById(id);
			produtoRepository.flush();	
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(PRODUTO_EM_USO, id));
		}
	}
}
