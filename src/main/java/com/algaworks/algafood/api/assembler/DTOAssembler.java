package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DTOAssembler<T, U> {
    
	@Autowired
    private ModelMapper modelMapper;
	
    public U toDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    
    public List<U> toListDTO(Collection<T> entities, Class<U> dtoClass) {
        return entities.stream()
                .map(entity -> toDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
