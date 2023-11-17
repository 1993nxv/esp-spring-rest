package com.algaworks.algafood.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	public List<Restaurante> findAll(){
		return restauranteRepository.findAll();
	}

	public Restaurante findById(Long id) {
		
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
			
		return restaurante
				.orElseThrow(() -> new EntidadeNaoEncontradaException
						("Restaurante com id:" + id + " não encontrado."));	
	}
	
	public Restaurante save(Restaurante restaurante) {
		Cozinha cozinha = cozinhaRepository
				.findById(restaurante
						.getCozinha()
						.getId())
						.orElseThrow(() -> new EntidadeNaoEncontradaException
								("Cozinha com id:" + restaurante.getCozinha().getId() + " não encontrada."));
		
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
	}
}
