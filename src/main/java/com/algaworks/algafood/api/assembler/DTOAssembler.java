package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DTOAssembler {
    
    private static ModelMapper modelMapper = new ModelMapper();
    
    public static <T, U> U toDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    
    public static <T, U> List<U> toListDTO(List<T> entities, Class<U> dtoClass) {
        return entities.stream()
                .map(entity -> toDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }
}
