package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.modelVO.CidadeVO;

@Component
public class CidadeVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade cidadeVOConverter(CidadeVO cidadeVO) {
		return modelMapper.map(cidadeVO, Cidade.class);
	}
	
	public void copyToDomainObj(CidadeVO cidadeVO, Cidade cidade) {
//		Evitando org.hibernate.HibernateException: identifier of an instance of 
//		com.algaworks.algafood.domain.model.Estado was altered from 1 to 2
		cidade.setEstado(new Estado());
		
		modelMapper.map(cidadeVO, cidade);
	}

}
