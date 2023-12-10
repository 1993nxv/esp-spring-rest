package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;


public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{
	
	
	List<Cozinha> findByNome(String name);
	
	List<Cozinha> findByNomeContaining(String name);
	
}
