package com.algaworks.algafood.domain.service.relatorio;

import java.util.List;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;


public interface VendaQueryService {
	
	List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset);
	
}
