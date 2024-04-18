package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOAssembler {
    
	@Autowired
    private ModelMapper modelMapper;
    
    public <T, U> U toDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    
    public <T, U> List<U> toListDTO(List<T> entities, Class<U> dtoClass) {
        return entities.stream()
                .map(entity -> toDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
