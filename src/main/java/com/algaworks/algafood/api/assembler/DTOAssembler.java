package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class DTOAssembler<T, U extends RepresentationModel<U>, C> extends RepresentationModelAssemblerSupport<T, U>{
    
    private ModelMapper modelMapper;
    private Class<C> controllerClass;
	
    public DTOAssembler(Class<U> dtoClass, Class<C> controllerClass, ModelMapper modelMapper) {
        super(controllerClass, dtoClass);
        this.modelMapper = modelMapper;
        this.controllerClass = controllerClass;
    }
	
    public U toDTO(T entity, Class<U> dtoClass) {

        U dto = modelMapper.map(entity, dtoClass);
        dto.add(WebMvcLinkBuilder.linkTo(this.controllerClass)
				.slash("CodigoOuId").withSelfRel());
        dto.add(WebMvcLinkBuilder.linkTo(this.controllerClass)
				.withSelfRel());
    	return dto;
    }
    
    public List<U> toListDTO(Collection<T> entities, Class<U> dtoClass) {
        return entities.stream()
                .map(entity -> toDTO(entity, dtoClass))
                .collect(Collectors.toList());
    }
    
    @Override
	public CollectionModel<U> toCollectionModel(Iterable<? extends T> entities) {
		return super.toCollectionModel(entities)
				.add(WebMvcLinkBuilder.linkTo(this.controllerClass)
				.withSelfRel());
	}

	@Override
	public U toModel(T entity) {
		return toDTO(entity, getResourceType());
	}
}
