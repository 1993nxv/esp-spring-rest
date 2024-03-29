package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.modelDTO.EnderecoDTO;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToDTOtypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToDTOtypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
