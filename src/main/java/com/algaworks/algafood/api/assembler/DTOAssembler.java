package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

public class DTOAssembler<T, U extends RepresentationModel<U>, C> extends RepresentationModelAssemblerSupport<T, U>{
    
    private ModelMapper modelMapper;
	
    public DTOAssembler(Class<U> dtoClass, Class<C> controllerClass, ModelMapper modelMapper) {
        super(controllerClass, dtoClass);
        this.modelMapper = modelMapper;
    }
	
    public U toDTO(T entity, Class<U> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }
    
    public List<U> toListDTO(Collection<T> entities, Class<U> dtoClass) {
        return entities.stream()
                .map(entity -> toDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }

	@Override
	public U toModel(T entity) {
		return null;
	}
}
