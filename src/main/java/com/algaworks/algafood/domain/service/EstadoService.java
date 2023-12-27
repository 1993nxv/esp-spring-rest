package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> findAll(){
		return estadoRepository.findAll();
	}

	public Estado findById(Long id) {	
		Optional<Estado> estado = estadoRepository.findById(id);		
		return estado
				.orElseThrow(() -> new EntidadeNaoEncontradaException
						("Estado com id:" + id + " não encontrado."));	
	}
	
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void deleteById(Long id) {
		try {
			Optional<Estado> estado = estadoRepository.findById(id);		
			if(!estado.isEmpty()) {
				estadoRepository.deleteById(id);
			} else {
				throw new EmptyResultDataAccessException(0);
			}			
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException("Cozinho com id:" + id + " não pode ser removido, pois está em uso.");
		}
	}
}
