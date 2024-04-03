package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.model.modelDTO.UsuarioDTO;

@Component
public class UsuarioDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO usuarioDTOConverter(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toListDTO(List<Usuario> usuarios){
		return usuarios.stream()
				.map(usuario -> usuarioDTOConverter(usuario))
				.collect(Collectors.toList());
	}
}
