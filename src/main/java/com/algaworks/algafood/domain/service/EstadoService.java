package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Estado com id:%d não encontrado.";

	private static final String MSG_ESTADO_EM_USO = "Estado com id:%d não pode ser removido, pois está em uso.";
	
	@Autowired
	EstadoRepository estadoRepository;
	
	public List<Estado> findAll(){
		return estadoRepository.findAll();
	}

	public Estado findById(Long id) {		
		return estadoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(MSG_ESTADO_NAO_ENCONTRADO, id)));	
	}
	
	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void deleteById(Long id) {
		findById(id);
		try {	
			estadoRepository.deleteById(id);
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}
	}
}
