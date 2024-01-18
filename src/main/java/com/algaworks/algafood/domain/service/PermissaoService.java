package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	public List<Permissao> findAll(){	
		return permissaoRepository.findAll();
	}
	
	public Permissao findById(Long id){	
			Optional<Permissao> Permissao = permissaoRepository.findById(id);		
			return Permissao
					.orElseThrow(() -> new NegocioException("Permissão com id:" + id + " não encontrada."));	
	}
	
	public Permissao save(Permissao permissao){
		return permissaoRepository.save(permissao);
	}
	
	public void deleteById(Long id) {
		try {
			Optional<Permissao> permissao = permissaoRepository.findById(id);		
			if(!permissao.isEmpty()) {
				permissaoRepository.deleteById(id);
			} else {
				throw new EmptyResultDataAccessException(0);
			}	
		} catch (EmptyResultDataAccessException error) {	
			throw new NegocioException("Permissão com id:" + id + " não encontrada.");
		} catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException("Permissão com id:" + id + " não pode ser removida, pois está em uso.");
		}
	}
}
