package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> findAll(){	
		return cozinhaRepository.findAll();
	}
	
	public Cozinha findById(Long id){	
			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);		
			return cozinha
					.orElseThrow(() -> new EntidadeNaoEncontradaException("Cozinha com id:" + id + " não encontrada."));	
	}
	
	public Cozinha save(Cozinha cozinha){
		return cozinhaRepository.save(cozinha);
	}
	
	public void deleteById(Long id) {
		try {
			cozinhaRepository.findById(id);		
			cozinhaRepository.deleteById(id);
						
		} catch (EmptyResultDataAccessException e) {
			
			throw new EntidadeNaoEncontradaException("Cozinha com id:" + id + " não encontrada.");
	
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException("Cozinha com id:" + id + " não pode ser removida, pois está em uso.");
		}
	}

	public List<Cozinha> findByNome(String nome) {	
		return cozinhaRepository.findByNome(nome);	
	}
	
	public List<Cozinha> findByNomeContaining(String nome) {	
		return cozinhaRepository.findByNomeContaining(nome);
	}

	public Optional<Cozinha> buscarPrimeiro() {	
		return cozinhaRepository.buscaPrimeiro();
	}
}
