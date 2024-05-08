package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.api.disassembler.VODisassembler;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.modelDTO.PedidoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoStatusDTO;
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
	private DTOAssembler<Pedido, PedidoStatusDTO> assemblerStatusDTO;
	
	@Autowired 
	private VODisassembler<PedidoVO, Pedido> disassemblerVO;
	
	@GetMapping
	public List<PedidoResumoDTO> findAll(){
		return assemblerResumoDTO.toListDTO(
				pedidoService.findAll(), PedidoResumoDTO.class);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){		
		Pedido pedido = pedidoService.findById(id);
		return ResponseEntity.ok(
				assemblerDTO.toDTO(pedido, PedidoDTO.class));						
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoResumoDTO save(@Valid @RequestBody PedidoVO pedidoVO){
		Pedido pedido = disassemblerVO.toEntity(pedidoVO, Pedido.class);
		return assemblerResumoDTO.toDTO(
				pedidoService.save(pedido), PedidoResumoDTO.class);
	}
	
	@PutMapping("/{id}/status/confirmar-pedido")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO confirmarPedido(@Valid @PathVariable Long id) {
		return assemblerStatusDTO.toDTO(
				pedidoService.confirmarPedido(id), PedidoStatusDTO.class);
	}
	
	@PutMapping("/{id}/status/pedido-entregue")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO pedidoEntregue(@Valid @PathVariable Long id) {
		return assemblerStatusDTO.toDTO(
				pedidoService.pedidoEntregue(id), PedidoStatusDTO.class);
	}
	
	@DeleteMapping("/{id}/status/cancelar-pedido")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO canelarPedido(@Valid @PathVariable Long id) {
		return assemblerStatusDTO.toDTO(
				pedidoService.cancelarPedido(id), PedidoStatusDTO.class);
	}

}
