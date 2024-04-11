package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.modelVO.ProdutoVO;

@Component
public class ProdutoVOdisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto produtoVOConverter(ProdutoVO produtoVO) {
		return modelMapper.map(produtoVO, Produto.class);
	}
	
	public void copyToDomainObj(ProdutoVO produtoVO, Produto produto) {
		modelMapper.map(produtoVO, produto);
	}

}
