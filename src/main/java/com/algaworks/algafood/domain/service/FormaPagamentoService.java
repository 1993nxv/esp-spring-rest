package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public List<FormaPagamento> findAll(){	
		return formaPagamentoRepository.findAll();
	}
	
	public FormaPagamento findById(Long id){	
			Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);			
			return formaPagamento
					.orElseThrow(() -> new NegocioException("Forma de Pagamento com id:" + id + " não encontrada."));	
	}
	
	public FormaPagamento save(FormaPagamento formaPagamento){
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public void deleteById(Long id) {
		try {
			Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);			
			if(!formaPagamento.isEmpty()) {
				formaPagamentoRepository.deleteById(id);
			} else {
				throw new EmptyResultDataAccessException(0);
			}		
		} catch (EmptyResultDataAccessException error) {	
			throw new NegocioException("Forma de Pagamento com id:" + id + " não encontrada.");
		} catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException("Forma de Pagamento com id:" + id + " não pode ser removida, pois está em uso.");
		}
	}
}
