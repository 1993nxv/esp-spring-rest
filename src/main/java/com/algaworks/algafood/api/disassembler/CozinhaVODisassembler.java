package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.modelVO.CozinhaVO;

@Component
public class CozinhaVODisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha cozinhaVOConverter(CozinhaVO cozinhaVO) {
		return modelMapper.map(cozinhaVO, Cozinha.class);
	}
	
	public void copyToDomainObj(CozinhaVO cozinhaVO, Cozinha cozinha) {
		modelMapper.map(cozinhaVO, cozinha);
	}
}
