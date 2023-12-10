package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {
	
	Optional<T> buscaPrimeiro();
	
}
