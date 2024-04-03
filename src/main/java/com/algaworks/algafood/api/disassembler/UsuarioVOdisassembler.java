package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelVO.UsuarioVO;

@Component
public class UsuarioVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario usuarioVOConverter(UsuarioVO usuarioVO) {
		return modelMapper.map(usuarioVO, Usuario.class);
	}

	public void copyToDomainObj(UsuarioVO usuarioVO, Usuario usuario) {
		modelMapper.map(usuarioVO, usuario);
	}

}
