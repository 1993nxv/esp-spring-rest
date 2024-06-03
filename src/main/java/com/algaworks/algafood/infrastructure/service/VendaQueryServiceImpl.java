package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.relatorio.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	
	@Autowired
	private EntityManager manager;
	
//	select date(p.data_criacao) as data_criacao,
//		count(p.id) as total_vendas,
//	    sum(p.valor_total) as total_faturado
//	from pedido p 
//	group by date(p.data_criacao)
	
	@Override
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter) {
		
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiariaDTO.class);
		
		var root = query.from(Pedido.class);
		
		var functionDateDataCriacao = builder.function(
				"date", 
				Date.class, 
				root.get("dataCriacao")
				);
		
		var selection = builder.construct(VendaDiariaDTO.class,
				functionDateDataCriacao, 
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal"))
				);
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		
		
		return manager.createQuery(query).getResultList();
	}
	
}
