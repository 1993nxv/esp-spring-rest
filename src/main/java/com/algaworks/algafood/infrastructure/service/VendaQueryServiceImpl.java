package com.algaworks.algafood.infrastructure.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.relatorio.VendaQueryService;

public class VendaQueryServiceImpl implements VendaQueryService{
	
	@Autowired
	private EntityManager manager;
	
	@Override
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter) {
		return null;
	}
	
}
