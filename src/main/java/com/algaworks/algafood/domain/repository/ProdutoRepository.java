package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{
		
	@Query("SELECT p FROM Produto p WHERE p.id = :produtoId AND p.restaurante = :restaurante")
	Optional<Produto> findProdutoByIdAndRestaurante(Long produtoId, Restaurante restaurante);
}
