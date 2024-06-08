package com.algaworks.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.domain.service.relatorio.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService{
	
	@Autowired
	private EntityManager manager;
	
	@Autowired
	private RestauranteService restauranteService;
	
//	select date(p.data_criacao) as data_criacao,
//		count(p.id) as total_vendas,
//	    sum(p.valor_total) as total_faturado
//	from pedido p 
//	group by date(p.data_criacao)
	
    @Override
    public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {
    	
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiariaDTO.class);
        var root = query.from(Pedido.class);
        
        var functionConvertTzDataCriacao = builder.function(
        		"convert_tz", Date.class, root.get("dataCriacao"),
        		builder.literal("+00:00"), builder.literal(timeOffset)
        );
        
        var functionDateDataCriacao = builder.function(
            "date",
            Date.class,
            functionConvertTzDataCriacao
        );

        var selection = builder.construct(
            VendaDiariaDTO.class,
            functionDateDataCriacao,
            builder.count(root.get("id")),
            builder.sum(root.get("valorTotal"))
        );

        query.select(selection);
        query.groupBy(functionDateDataCriacao);

        var predicates = new ArrayList<Predicate>();
        if (filter.getRestauranteId() != null) {
        	restauranteService.findById(filter.getRestauranteId());
            predicates.add(builder.equal(root.get("restaurante").get("id"), filter.getRestauranteId()));
        }
        if (filter.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoInicio()));
        }
        if (filter.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filter.getDataCriacaoFim()));
        }
        
        var statusPredicate = builder.or(
            builder.equal(root.get("status"), StatusPedido.CONFIRMADO),
            builder.equal(root.get("status"), StatusPedido.ENTREGUE)
        );
        
        predicates.add(statusPredicate);
        query.where(predicates.toArray(new Predicate[0]));

        return manager.createQuery(query).getResultList();
    }
}
