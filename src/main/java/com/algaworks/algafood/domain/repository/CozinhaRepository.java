package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.domain.model.Cozinha;


public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	List<Cozinha> findByNome(String name);	
	Page<Cozinha> findByNomeContaining(String name, Pageable pageable);
	
}
