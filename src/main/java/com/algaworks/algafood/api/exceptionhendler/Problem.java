package com.algaworks.algafood.api.exceptionhendler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {
	
	@ApiModelProperty(example = "2024-08-02T01:35:18Z", position = 1)
	private OffsetDateTime timeStamp;
	
	@ApiModelProperty(example = "400", position = 2)
	private Integer status;
	@ApiModelProperty(example = "/requisicao-invalida", position = 3)
	private String type;
	
	@ApiModelProperty(example = "Requisição inválida", position = 4)
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos...", position = 5)
	private String detail;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos...", position = 6)
	private String userMessage;
	
	@ApiModelProperty(value = "Lista ou campos que geraram o erro (opcional)", position = 7)
	private List<Object> objects;
	
	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {
		@ApiModelProperty(example = "preco")
		private String name;
		@ApiModelProperty(example = "O preco é obrigatório")
		private String userMessage;
	}
		
}
