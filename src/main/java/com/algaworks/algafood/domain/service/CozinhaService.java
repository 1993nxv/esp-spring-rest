package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha de id:%d não encontrada.";
	private static final String MSG_COZINHA_EM_USO = "Cozinha com id:%d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> findAll(){	
		return cozinhaRepository.findAll();
	}
	
	public Cozinha findById(Long id){	
			Optional<Cozinha> cozinha = cozinhaRepository.findById(id);		
			return cozinha
					.orElseThrow(() -> new EntidadeNaoEncontradaException(
							String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));	
	}
	
	public Cozinha save(Cozinha cozinha){
		return cozinhaRepository.save(cozinha);
	}
	
	public void deleteById(Long id) {
		
		cozinhaRepository.findById(id);	
		try {	
			cozinhaRepository.deleteById(id);
				
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id));
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
