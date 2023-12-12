package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Cidade> findAll(){	
		return cidadeRepository.findAll();
	}
	
	public Cidade findById(Long id){	
			Optional<Cidade> cidade = cidadeRepository.findById(id);		
			return cidade
					.orElseThrow(() -> new EntidadeNaoEncontradaException
							("Cidade com id:" + id + " não encontrada."));	
	}
	
	public Cidade save(Cidade cidade){	
		Estado estado = estadoRepository
				.findById(cidade
						.getEstado()
						.getId())
						.orElseThrow(() -> new EntidadeNaoEncontradaException
								("Estado com id:" + cidade.getEstado().getId() + " não encontrado."));	
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public Cidade updatePartially(Long id, Cidade cidade) {	
		findById(id);
		cidade.setId(id);
		return save(cidade);	
	}
	
	public void deleteById(Long id) {
		try {
			Optional<Cidade> cidade = cidadeRepository.findById(id);
			if(!cidade.isEmpty()) {
				cidadeRepository.deleteById(id);
			} else {
				throw new EmptyResultDataAccessException(0);
			}	
		} catch (EmptyResultDataAccessException error) {		
			throw new EntidadeNaoEncontradaException
			("Cidade com id:" + id + " não encontrada.");
		}
	}
}
