package com.algaworks.algafood.domain.service;

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
	
	public Cozinha save(Cozinha cozinha){
		return cozinhaRepository.save(cozinha);
	}
	
	public void deleteById(Long id) {
		try {
			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
			
			if(!cozinha.isEmpty()) {
				cozinhaRepository.deleteById(id);
			} else {
				throw new EmptyResultDataAccessException(0);
			}
			
		} catch (EmptyResultDataAccessException error) {
			
			throw new EntidadeNaoEncontradaException("Entidade não encontrada!");
		
		} catch (DataIntegrityViolationException error) {
			
			throw new EntidadeEmUsoException("Cozinha de código " + id + " não pode ser removida, pois está em uso.");
		
		}
	}
}
