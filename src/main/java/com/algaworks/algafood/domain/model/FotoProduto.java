package com.algaworks.algafood.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class FotoProduto {
	
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	private Produto produto;
	
	private String nomeArquivo;
	private String descricao;
	private String contentType;
	private Long tamanho;
}
