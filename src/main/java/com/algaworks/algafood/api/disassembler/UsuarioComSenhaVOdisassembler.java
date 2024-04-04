package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelVO.UsuarioComSenhaVO;

@Component
public class UsuarioComSenhaVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario usuarioVOConverter(UsuarioComSenhaVO usuarioVO) {
		return modelMapper.map(usuarioVO, Usuario.class);
	}

	public void copyToDomainObj(UsuarioComSenhaVO usuarioVO, Usuario usuario) {
		modelMapper.map(usuarioVO, usuario);
	}

}
