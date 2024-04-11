package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.modelDTO.ProdutoDTO;

@Component
public class ProdutoDTOassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO produtoDTOConverter(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toListDTO(List<Produto> produtos){
		return produtos.stream()
				.map(produto -> produtoDTOConverter(produto))
				.collect(Collectors.toList()
				);
	}
}
