package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.modelDTO.FormaPagamentoDTO;

@Component
public class FormaPagementoDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoDTO formaPagamentoDTOassembler(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toListDTO(Collection<FormaPagamento> formaPagamentos) {
		return formaPagamentos.stream()
				.map(formaPagamento -> formaPagamentoDTOassembler(formaPagamento))
				.collect(Collectors.toList());
	}
}
