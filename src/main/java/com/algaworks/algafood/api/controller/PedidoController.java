package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.api.disassembler.VODisassembler;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.modelDTO.PedidoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.modelVO.PedidoVO;
import com.algaworks.algafood.domain.service.PedidoService;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
		
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoDTO> assemblerDTO;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoResumoDTO> assemblerResumoDTO;
	
	@Autowired 
	private VODisassembler<PedidoVO, Pedido> disassemblerVO;
	
	@GetMapping
	public List<PedidoResumoDTO> findAll(){
		return assemblerResumoDTO.toListDTO(pedidoService.findAll(), PedidoResumoDTO.class);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){		
		Pedido pedido = pedidoService.findById(id);
		return ResponseEntity.ok(assemblerDTO.toDTO(pedido, PedidoDTO.class));						
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO save(@RequestBody PedidoVO pedidoVO){
		Pedido pedido = disassemblerVO.converterVO(pedidoVO, Pedido.class);
		return assemblerDTO.toDTO(pedidoService.save(pedido), PedidoDTO.class);
	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteById(@PathVariable Long id){
//		try {			
//			permissaoService.deleteById(id);
//			return ResponseEntity.noContent().build();		
//		} catch (EntidadeNaoEncontradaException e) {			
//			return ResponseEntity.notFound().build();	
//		} catch (EntidadeEmUsoException e) {		
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
}
