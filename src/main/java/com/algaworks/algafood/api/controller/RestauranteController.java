package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.modelDTO.RestauranteDTO;
import com.algaworks.algafood.domain.model.modelVO.RestauranteVO;
import com.algaworks.algafood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	RestauranteService restauranteService;
	
	@Autowired
	RestauranteDTOassembler restauranteDTOAssembler;
	@Autowired
	RestauranteVOdisassembler restauranteVOdisassembler;
	
	@GetMapping
	public List<RestauranteDTO> findAll(){
		return restauranteDTOAssembler.toListDTO(restauranteService.findAll());
	}
	
	
	@GetMapping("/{id}")
	public RestauranteDTO findById(@PathVariable Long id) {
		return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO save(@RequestBody @Valid RestauranteVO restauranteVO) {
		Restaurante restaurante = restauranteVOdisassembler.restauranteVOConverter(restauranteVO);
		try {	
			return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.save(restaurante));
		} catch (CozinhaNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteDTO update(
				@PathVariable Long id, 
				@RequestBody @Valid RestauranteVO restauranteVO) {
		
			Restaurante restauranteAtual = restauranteService.findById(id);
			
			
//			BeanUtils.copyProperties(
//					restauranteVOdisassembler.restauranteVOConverter(restauranteVO), restauranteAtual,
//					"id", "formasPagamento", "endereco", "dataCadastro");
			restauranteVOdisassembler.copyToDomainObj(restauranteVO, restauranteAtual);
			try {
				return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.save(restauranteAtual));
			} catch (CozinhaNaoEncontradoException e) {
				throw new NegocioException(e.getMessage(), e);
			}
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable Long id) {
			findById(id);
			restauranteService.deleteById(id);	
	}
	
	@PatchMapping("/{id}")
	public RestauranteDTO updatePartially(
				@PathVariable Long id,
				@RequestBody Map<String, Object> campos,
				HttpServletRequest request){		
		
			Restaurante restaurante = restauranteService.findById(id);
			return restauranteDTOAssembler.restauranteDTOConverter(
					restauranteService.updatePartially(restaurante, campos, request));			
	}
	
	@GetMapping("/por-taxa")
	public List<RestauranteDTO> findByTaxaFreteBetween
		(@RequestParam BigDecimal taxaInicial, @RequestParam BigDecimal taxaFinal){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findByTaxaFreteBetween(taxaInicial, taxaFinal));
	}
	
	@GetMapping("/por-nome-e-id")
	public List<RestauranteDTO> porNomeAndCozinhaId(@RequestParam String nome, @RequestParam Long cozinhaId){
		return restauranteDTOAssembler.toListDTO(restauranteService.porNomeAndCozinhaId(nome, cozinhaId));
	}
	
	@GetMapping("/findImp")
	public List<RestauranteDTO> findImpl(
			 String nome, 
			 BigDecimal taxaFreteInicial, 
			 BigDecimal taxaFreteFinal){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findImpl(nome, taxaFreteInicial, taxaFreteFinal));
	}
	
	@GetMapping("/frete-gratis")
	public List<RestauranteDTO> findImpl(String nome){		
		return restauranteDTOAssembler.toListDTO(restauranteService.findFreteGratis(nome));
	}
	
	@GetMapping("/primeiro")
	public RestauranteDTO buscarPrimeiro(){	
		return restauranteDTOAssembler.restauranteDTOConverter(restauranteService.buscarPrimeiro());	
	}
	
}
