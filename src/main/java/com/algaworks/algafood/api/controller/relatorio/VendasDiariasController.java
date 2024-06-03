package com.algaworks.algafood.api.controller.relatorio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.infrastructure.service.VendaQueryServiceImpl;



@RestController
@RequestMapping("/relatorio")
public class VendasDiariasController {
		
	@Autowired
	private VendaQueryServiceImpl vendaQueryServiceImpl;
	
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter){			
			return vendaQueryServiceImpl.consultarVendasDiarias(filter);
	}
		
}
