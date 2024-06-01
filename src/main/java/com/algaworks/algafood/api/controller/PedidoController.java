package com.algaworks.algafood.api.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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



@RestController
@RequestMapping("/pedidos")
public class PedidoController {
		
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoDTO> assemblerDTO;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoResumoDTO> assemblerResumoDTO;
	
	@Autowired 
	private DTOAssembler<Pedido, PedidoStatusDTO> assemblerStatusDTO;
	
	@Autowired 
	private VODisassembler<PedidoVO, Pedido> disassemblerVO;
	
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
	
//	@GetMapping("/projecao")
//	public MappingJacksonValue findAllWhitProjecao(@RequestParam(required = false) String campos){
//		List<Pedido> pedidos = pedidoService.findAll();
//		List<PedidoResumoDTO> pedidosDTO = assemblerResumoDTO.toListDTO(pedidos, PedidoResumoDTO.class);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDTO);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//		
//		if(StringUtils.isNotBlank(campos)) {
//			filterProvider.addFilter("pedidoFilter",
//					SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(","))
//				);
//		}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		return pedidosWrapper;
//		
//	}
	
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
