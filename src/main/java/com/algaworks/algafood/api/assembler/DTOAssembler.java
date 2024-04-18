package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DTOAssembler<T, U> {
    
	@Autowired
    private ModelMapper modelMapper;
	
    public U toDTO(T entity, Class<U> dto) {
        return modelMapper.map(entity, dto);
    }
    
    public List<U> toListDTO(List<T> entities, Class<U> dto) {
        return entities.stream()
                .map(entity -> toDTO(entity, dto))
                .collect(Collectors.toList());
    }
}
