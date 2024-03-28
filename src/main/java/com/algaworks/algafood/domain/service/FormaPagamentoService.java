package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class FormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Forma de pagamento com id:%d não pode ser removida, pois está em uso.";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public List<FormaPagamento> findAll(){	
		return formaPagamentoRepository.findAll();
	}
	
	public FormaPagamento findById(Long id){	
			Optional<FormaPagamento> formaPagamento = formaPagamentoRepository.findById(id);			
			return formaPagamento
					.orElseThrow(() -> new FormaPagamentoNaoEncontradoException(id));	
	}
	
	@Transactional
	public FormaPagamento save(FormaPagamento formaPagamento){
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	@Transactional
	public void deleteById(Long id) {
		findById(id);	
		try {	
			formaPagamentoRepository.deleteById(id);
			formaPagamentoRepository.flush();	
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
		}
	}
}
