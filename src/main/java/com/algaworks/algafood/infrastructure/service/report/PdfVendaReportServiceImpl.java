package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.ReportException;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.relatorio.VendaQueryService;
import com.algaworks.algafood.domain.service.relatorio.VendaReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfVendaReportServiceImpl implements VendaReportService {
	
	
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		
		var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias-relatorio.jasper");
		
		var parametros = new HashMap<String, Object>();
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));
		
		var vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
		var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
		
		try {
			var jasperPrint  = JasperFillManager.fillReport(
					inputStream, 
					parametros, 
					dataSource
			);
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas");
		}
		
	}

}
