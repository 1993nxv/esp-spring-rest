package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.modelDTO.PedidoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.modelDTO.PedidoStatusDTO;
import com.algaworks.algafood.domain.model.modelVO.PedidoVO;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.PedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import com.google.common.collect.ImmutableMap;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
		
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	@Qualifier("pedidoDTOAssembler")
	private DTOAssembler<Pedido, PedidoDTO, PedidoController> assemblerDTO;

	@Autowired
	@Qualifier("pedidoResumoDTOAssembler")
	private DTOAssembler<Pedido, PedidoResumoDTO, PedidoController> assemblerResumoDTO;

	@Autowired
	@Qualifier("pedidoStatusDTOAssembler")
	private DTOAssembler<Pedido, PedidoStatusDTO, PedidoController> assemblerStatusDTO;
	
	@Autowired 
	private VODisassembler<PedidoVO, Pedido> disassemblerVO;
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
		name = "campos",
		paramType = "query",
		type = "String",
		example = "name,asc")
	})
	@GetMapping
	public Page<PedidoResumoDTO> findAll(@PageableDefault(size = 2) Pageable pageable, PedidoFilter filter){
		pageable = traduzirPageable(pageable);
		Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);
		List<PedidoResumoDTO> pedidosResumoDTO = assemblerResumoDTO.toListDTO(pedidosPage.getContent(), PedidoResumoDTO.class);
		Page<PedidoResumoDTO> pedidosResumoDTOpage = new PageImpl<>(
				pedidosResumoDTO,
				pageable,
				pedidosPage.getTotalElements());
		return pedidosResumoDTOpage;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
		name = "campos",
		paramType = "query",
		type = "String",
		example = "name,asc")
	})
	@GetMapping("/{pedidoCodigo}")
	public ResponseEntity<?> findByCodigo(@PathVariable String pedidoCodigo){		
		Pedido pedido = pedidoService.findByCodigo(pedidoCodigo);
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
	
	@PutMapping("/{pedidoCodigo}/status/confirmar-pedido")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO confirmarPedido(@Valid @PathVariable String pedidoCodigo) {
		return assemblerStatusDTO.toDTO(
				pedidoService.confirmarPedido(pedidoCodigo), PedidoStatusDTO.class);
	}
	
	@PutMapping("/{pedidoCodigo}/status/pedido-entregue")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO pedidoEntregue(@Valid @PathVariable String pedidoCodigo) {
		return assemblerStatusDTO.toDTO(
				pedidoService.pedidoEntregue(pedidoCodigo), PedidoStatusDTO.class);
	}
	
	@DeleteMapping("/{pedidoCodigo}/status/cancelar-pedido")
	@ResponseStatus(HttpStatus.OK)
	public PedidoStatusDTO canelarPedido(@Valid @PathVariable String pedidoCodigo) {
		return assemblerStatusDTO.toDTO(
				pedidoService.cancelarPedido(pedidoCodigo), PedidoStatusDTO.class);
	}
	
	private Pageable traduzirPageable(Pageable pageable) {
		var mapemento = ImmutableMap.of(
			"codigo", "codigo",
			"nomeCliente", "cliente.nome",
			"restaurante.nome", "restaurante.nome",
			"valorTotal", "valorTotal"
		    );
		
		return PageableTranslator.translate(pageable, mapemento);
	}

}
