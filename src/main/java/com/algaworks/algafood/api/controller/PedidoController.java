package com.algaworks.algafood.api.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.DTOAssembler;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.modelDTO.PedidoDTO;
import com.algaworks.algafood.domain.service.PedidoService;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
		
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoDTO> assemblerDTO;
	
	@GetMapping
	public List<PedidoDTO> findAll(){
		return assemblerDTO.toListDTO(pedidoService.findAll(), PedidoDTO.class);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {			
			Pedido pedido = pedidoService.findById(id);
			return ResponseEntity.ok(assemblerDTO.toDTO(pedido, PedidoDTO.class));			
		} catch (EntidadeNaoEncontradaException e) {			
			return ResponseEntity
					.badRequest()
					.body(e.getMessage());			
		}			
	}
	
//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public PedidoDTO save(@RequestBody PedidoVO pedidoVO){
//		return assemblerDTO.toDTO(pedidoService.save(pedidoVO), PedidoDTO.class);
//	}
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
