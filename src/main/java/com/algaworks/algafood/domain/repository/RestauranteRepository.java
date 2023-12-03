package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends 
				JpaRepository<Restaurante, Long>,
				RestauranteRepositoryQueries {
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
	List<Restaurante> porNomeAndCozinhaId(String nome, Long cozinhaId);
	
//	public List<Restaurante> findImpl(
//			String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
}
