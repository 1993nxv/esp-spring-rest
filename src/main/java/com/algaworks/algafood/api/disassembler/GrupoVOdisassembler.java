package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.modelVO.GrupoVO;

@Component
public class GrupoVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo grupoVOConverter(GrupoVO grupoVO) {
		return modelMapper.map(grupoVO, Grupo.class);
	}
	
	public void copyToDomainObj(GrupoVO grupoVO, Grupo grupo) {
		modelMapper.map(grupoVO, grupo);
	}

}
