package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade com id:%d não encontrada.";

	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Estado com id:%d não encontrado.";
	
	private static final String MSG_CIDADE_EM_USO = "Cidade com id:%d não pode ser removida, pois está em uso.";
	
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
							(String.format(MSG_CIDADE_NAO_ENCONTRADA, id)));	
	}
	
	public Cidade save(Cidade cidade){	
		Estado estado = estadoRepository
				.findById(cidade
						.getEstado()
						.getId())
						.orElseThrow(() -> new EntidadeNaoEncontradaException(
								String.format(MSG_ESTADO_NAO_ENCONTRADO, cidade.getEstado().getId()
						)));	
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public Cidade updatePartially(Long id, Cidade cidade) {	
		findById(id);
		cidade.setId(id);
		return save(cidade);	
	}
	
	public void deleteById(Long id) {
		cidadeRepository.findById(id);
		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}	
	}

}
	

