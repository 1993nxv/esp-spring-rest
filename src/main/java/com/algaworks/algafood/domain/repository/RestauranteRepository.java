package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.repository.query.Param;

public interface RestauranteRepository extends 
				CustomJpaRepository<Restaurante, Long>,
				RestauranteRepositoryQueries,
				JpaSpecificationExecutor<Restaurante> {
	
	
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	@Query("from Restaurante r join fetch r.cozinha left outer join fetch r.formasPagamento left outer join fetch r.endereco")
	Optional<Restaurante> findById();
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId")
	List<Restaurante> porNomeAndCozinhaId(String nome, Long cozinhaId);

	@Query(name = "Restaurante.existsResponsavel")
	boolean existsResponsavel(Long restauranteId, Long usuarioId);

}
