package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradoException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade com id:%d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	public List<Cidade> findAll(){	
		return cidadeRepository.findAll();
	}
	
	public Cidade findById(Long id){		
			return cidadeRepository.findById(id)
					.orElseThrow(() -> new CidadeNaoEncontradoException(id));	
	}
	
	@Transactional
	public Cidade save(Cidade cidade){	
		Estado estado = estadoService.findById(cidade.getEstado().getId());	
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	@Transactional
	public Cidade updatePartially(Long id, Cidade cidade) {	
		Cidade cidadeAtual = findById(id);
		cidade.setId(id);
		if (cidade.getEstado() == null) {
			cidade.setEstado(cidadeAtual.getEstado());
		}
		return save(cidade);	
	}
	
	@Transactional
	public void deleteById(Long id) {
		cidadeRepository.findById(id);
		cidadeRepository.flush();
		try {
			cidadeRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}	
	}

}
	

