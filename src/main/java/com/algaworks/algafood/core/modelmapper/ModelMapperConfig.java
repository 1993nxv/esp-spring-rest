package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.modelDTO.EnderecoDTO;
import com.algaworks.algafood.domain.model.modelVO.ItemPedidoVO;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToDTOtypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		modelMapper.createTypeMap(ItemPedidoVO.class, ItemPedido.class)
				.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		enderecoToDTOtypeMap.<String>addMapping(
				src -> src.getCidade().getEstado().getNome(),
				(dest, value) -> dest.getCidade().setEstado(value));
		
		return modelMapper;
	}
}
