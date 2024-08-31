package com.algaworks.algafood.api.assembler.config;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.modelDTO.PedidoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoStatusDTO;

@Component
public class QualifierInjectDTO {
	
	@Component
	public class PedidoDTOAssembler extends DTOAssembler<Pedido, PedidoDTO, PedidoController> {
	    
	    public PedidoDTOAssembler(ModelMapper modelMapper) {
	        super(Pedido.class, PedidoDTO.class, PedidoController.class, modelMapper);
	    }
	    
	}

	@Component
	public class PedidoResumoDTOAssembler extends DTOAssembler<Pedido, PedidoResumoDTO, PedidoController> {
	  
	    public PedidoResumoDTOAssembler(ModelMapper modelMapper) {
	        super(Pedido.class, PedidoResumoDTO.class, PedidoController.class, modelMapper);
	    }
	    
	}

	@Component
	public class PedidoStatusDTOAssembler extends DTOAssembler<Pedido, PedidoStatusDTO, PedidoController> {
	   
	    public PedidoStatusDTOAssembler(ModelMapper modelMapper) {
	        super(Pedido.class, PedidoStatusDTO.class, PedidoController.class, modelMapper);
	    }
	    
	}
}
