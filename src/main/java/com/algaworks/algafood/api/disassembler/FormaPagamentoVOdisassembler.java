package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.modelVO.FormaPagamentoVO;

@Component
public class FormaPagamentoVOdisassembler {
	
	@Autowired
	ModelMapper modelMapper;
	
	public FormaPagamento formaPagamentoVOconverter(FormaPagamentoVO formaPagamentoVO) {
		return modelMapper.map(formaPagamentoVO, FormaPagamento.class);
	}
	
	public void copyToDomainObj (FormaPagamentoVO formaPagamentoVO, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoVO, formaPagamento);
	}
}
