package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VODisassembler<T, U> {
	@Autowired
	private ModelMapper modelMapper;
	
	public U toEntity(T entityVO, Class<U> entityClass) {
		return modelMapper.map(entityVO, entityClass);
	}
	
	public void copyToEntity(T entityVO, U entity) {
		modelMapper.map(entityVO, entity);
	}
}
