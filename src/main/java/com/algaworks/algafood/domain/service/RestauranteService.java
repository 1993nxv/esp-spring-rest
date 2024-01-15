package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Restaurante com id:%d não encontrado.";

	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaService cozinhaService;
	
	public List<Restaurante> findAll(){
		return restauranteRepository.findAll();
	}

	public Restaurante findById(Long id) {			
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException
						(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));	
	}
	
	public Restaurante save(Restaurante restaurante) {		
		Cozinha cozinha = cozinhaService
				.findById(restaurante.getCozinha().getId());
		restaurante.setCozinha(cozinha);	
		return restauranteRepository.save(restaurante);
	}

	public void deleteById(Long id) {		
			findById(id);
			restauranteRepository.deleteById(id);		
	}

	public Restaurante updatePartially(Restaurante restaurante, Map<String, Object> campos) {	
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
		
		campos.forEach((nomePropriedade, valorPropriedade) -> {			
			Field field  = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			ReflectionUtils.setField(field, restaurante, novoValor);			
		});		
		return restauranteRepository.save(restaurante);
	}

	public List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {	
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}

	public List<Restaurante> porNomeAndCozinhaId(String nome, Long cozinhaId) {		
		return restauranteRepository.porNomeAndCozinhaId(nome, cozinhaId);
	}
	
	public List<Restaurante> findImpl(
			 String nome, 
			 BigDecimal taxaFreteInicial, 
			 BigDecimal taxaFreteFinal){		
		return restauranteRepository.findImpl(nome, taxaFreteInicial, taxaFreteFinal);
	}

	public List<Restaurante> findFreteGratis(String nome) {
		return restauranteRepository.findComFreteGratis(nome);
	}

	public Optional<Restaurante> buscarPrimeiro() {
		return restauranteRepository.buscaPrimeiro();
	}
}
