package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Query("from Pedido p "
			+ "join fetch p.cliente "
			+ "join fetch p.restaurante r "
			+ "join fetch r.cozinha")
	List<Pedido> findAll();
	
	@Query("select p from Pedido p "
		    + "join fetch p.cliente "
		    + "join fetch p.restaurante r "
		    + "join fetch r.cozinha "
		    + "join fetch r.endereco e "
		    + "join fetch e.cidade c "
		    + "join fetch c.estado "
		    + "join fetch p.itens " 
		    + "join fetch p.formaPagamento "
		    + "where p.id = :id")
	Optional<Pedido> findById(@Param("id") Long id);
}
