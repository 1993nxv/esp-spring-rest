package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.modelVO.EstadoVO;

@Component
public class EstadoVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado estadoVOconverter(EstadoVO estadoVO) {
		return modelMapper.map(estadoVO, Estado.class);
	}
	
	public void copyToDomainObj(EstadoVO estadoVO, Estado estado) {
		modelMapper.map(estadoVO, estado);
	}
}
