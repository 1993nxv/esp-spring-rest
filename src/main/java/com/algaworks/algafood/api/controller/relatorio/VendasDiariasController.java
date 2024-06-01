package com.algaworks.algafood.api.controller.relatorio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDTOassembler;
import com.algaworks.algafood.api.disassembler.CidadeVOdisassembler;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.CidadeService;
import com.algaworks.algafood.domain.service.relatorio.VendaQueryService;



@RestController
@RequestMapping("/relatorio")
public class VendasDiariasController {
		
	@Autowired
	private VendaQueryService vendaQueryService;
	
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filter){			
			return vendaQueryService.consultarVendasDiarias(filter);
	}
		
}
