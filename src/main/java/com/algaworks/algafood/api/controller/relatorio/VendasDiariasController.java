package com.algaworks.algafood.api.controller.relatorio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.relatorioDTO.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.relatorio.VendaReportService;
import com.algaworks.algafood.infrastructure.service.query.VendaQueryServiceImpl;



@RestController
@RequestMapping("/relatorio")
public class VendasDiariasController {
		
	@Autowired
	private VendaQueryServiceImpl vendaQueryServiceImpl;
	
	@Autowired 
	private VendaReportService vendaReportService;
	
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiariaDTO> consultarVendasDiarias(
			VendaDiariaFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return vendaQueryServiceImpl.consultarVendasDiarias(filter, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(
			VendaDiariaFilter filter, 
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filter, timeOffset);
		
		var headers = new HttpHeaders();
		
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
		
}
