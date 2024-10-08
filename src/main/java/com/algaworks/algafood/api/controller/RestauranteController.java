package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.algaworks.algafood.core.security.CheckSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteDTOassembler;
import com.algaworks.algafood.api.disassembler.RestauranteVOdisassembler;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteDTO;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteNomesDTO;
import com.algaworks.algafood.domain.model.modelVO.RestauranteVO;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@Autowired
	RestauranteDTOassembler restauranteDTOAssembler;
	
	@Autowired
	RestauranteVOdisassembler restauranteVOdisassembler;


	@ApiOperation(value = "Lista restaurantes")
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome da projeção de pedidos",
				name = "projecao",
				paramType = "query",
				type = "String",
				allowableValues = "apenas-nomes",
				required = false
		)
	})

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public List<RestauranteDTO> findAll(){
		List<RestauranteDTO> restaurantes = restauranteDTOAssembler.toListDTO(restauranteService.findAll());
		return restaurantes;
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@ApiOperation(value = "Lista restaurantes", hidden = true)
	@JsonView(RestauranteNomesDTO.class)
	@GetMapping(params = "projecao=apenas-nomes")
	public List<Restaurante> findAllNomes(){
		List<Restaurante> restaurantes = restauranteService.findAll();
		return restaurantes;
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{id}")
	public RestauranteDTO findById(@PathVariable Long id) {
		return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.findById(id));
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO save(@RequestBody @Valid RestauranteVO restauranteVO) {
		Restaurante restaurante = restauranteVOdisassembler.restauranteVOConverter(restauranteVO);
		try {	
			return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.save(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{id}")
	public RestauranteDTO update(
				@PathVariable Long id, 
				@RequestBody @Valid RestauranteVO restauranteVO) {
		
			Restaurante restauranteAtual = restauranteService.findById(id);
			
			restauranteVOdisassembler.copyToDomainObj(restauranteVO, restauranteAtual);
			try {
				return restauranteDTOAssembler
						.restauranteDTOConverter(
								restauranteService.save(restauranteAtual));
			} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
				throw new NegocioException(e.getMessage(), e);
			}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
			findById(id);
			restauranteService.deleteById(id);	
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PatchMapping("/{id}")
	public RestauranteDTO updatePartially(
				@PathVariable Long id,
				@RequestBody Map<String, Object> campos,
				HttpServletRequest request){		
		
			Restaurante restaurante = restauranteService.findById(id);
			return restauranteDTOAssembler.restauranteDTOConverter(
					restauranteService.updatePartially(restaurante, campos, request));			
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{id}/ativar")
	public void ativacao(@PathVariable Long id) {
		restauranteService.ativacao(id);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/ativar/todos")
	@ResponseStatus(HttpStatus.OK)
	public void ativacaoEmMassa() {
		List<Long> ids = new ArrayList<>();
		findAll().forEach(restaurante -> ids.add(restaurante.getId()));
		restauranteService.ativacaoEmMassa(ids);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/{id}/inativar")
	public void inativacao(@PathVariable Long id) {
		restauranteService.inativacao(id);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/inativar/todos")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativacaoEmMassa() {
		List<Long> ids = new ArrayList<>();
		findAll().forEach(restaurante -> ids.add(restaurante.getId()));
		restauranteService.inativacaoEmMassa(ids);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{restaurenteId}/abertura")
	public void abrirRestaurante(@PathVariable Long restaurenteId) {
		restauranteService.abrirRestaurante(restaurenteId);
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{restaurenteId}/fechamento")
	public void fecharRestaurante(@PathVariable Long restaurenteId) {
		restauranteService.fecharRestaurante(restaurenteId);
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/por-taxa")
	public List<RestauranteDTO> findByTaxaFreteBetween
		(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/por-nome-e-id")
	public List<RestauranteDTO> porNomeAndCozinhaId(@RequestParam String nome, @RequestParam Long cozinhaId){
		return restauranteDTOAssembler.toListDTO(restauranteService.porNomeAndCozinhaId(nome, cozinhaId));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/findImp")
	public List<RestauranteDTO> findImpl(
			 String nome, 
			 BigDecimal taxaFreteInicial, 
			 BigDecimal taxaFreteFinal){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findImpl(nome, taxaFreteInicial, taxaFreteFinal));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/frete-gratis")
	@ResponseStatus(HttpStatus.CREATED)
	public List<RestauranteDTO> findImpl(String nome){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findFreteGratis(nome));
	}

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/primeiro")
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO buscarPrimeiro(){	
		return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.buscarPrimeiro());	
	}
	
}
