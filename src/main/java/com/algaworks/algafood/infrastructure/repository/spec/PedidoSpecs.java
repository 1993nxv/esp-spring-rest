package com.algaworks.algafood.infrastructure.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.google.common.base.Predicate;

public class PedidoSpecs {
	
	public static Specification<Pedido> usandoFiltro(PedidoFilter filter) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			
			if(filter.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filter.getClienteId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
			
	}
	
}
