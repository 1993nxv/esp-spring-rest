package com.algaworks.algafood.api.controller.relatorio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDTOassembler;
import com.algaworks.algafood.api.disassembler.CidadeVOdisassembler;
import com.algaworks.algafood.domain.model.modelDTO.CidadeDTO;
import com.algaworks.algafood.domain.service.CidadeService;



@RestController
@RequestMapping("/relatorio")
public class VendasDiariasController {
		
	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDTOassembler cidadeDTOassembler;
	
	@Autowired 
	private CidadeVOdisassembler cidadeVOdisassembler;
	
	
	@GetMapping("/vendas-diarias")
	public CidadeDTO findById(@PathVariable Long id){			
			return cidadeDTOassembler.cidadeDTOConverter(
					cidadeService.findById(id));		
	}
		
}
