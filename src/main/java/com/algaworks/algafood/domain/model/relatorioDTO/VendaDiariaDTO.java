package com.algaworks.algafood.domain.model.relatorioDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class VendaDiariaDTO {
	
	private LocalDate data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
	
}
