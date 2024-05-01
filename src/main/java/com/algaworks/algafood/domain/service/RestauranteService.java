package com.algaworks.algafood.domain.service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestauranteService {
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaService cozinhaService;
	
	@Autowired
	CidadeService cidadeService;
	
	@Autowired
	FormaPagamentoService formaPagamentoService;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	private SmartValidator validator;
	
	public List<Restaurante> findAll(){
		return restauranteRepository.findAll();
	}

	public Restaurante findById(Long id) {
		return restauranteRepository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(id));
	}
	
	@Transactional
	public Restaurante save(Restaurante restaurante) {		
		Cozinha cozinha = cozinhaService.findById(
				restaurante.getCozinha().getId());
		Cidade cidade = cidadeService.findById(
				restaurante.getEndereco().getCidade().getId());
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void deleteById(Long id) {		
			findById(id);
			restauranteRepository.deleteById(id);		
	}
	
	@Transactional
	public Restaurante updatePartially(
			Restaurante restaurante, 
			Map<String, Object> campos, 
			HttpServletRequest request) {	
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
			
			campos.forEach((nomePropriedade, valorPropriedade) -> {			
				Field field  = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				ReflectionUtils.setField(field, restaurante, novoValor);});			
				
		}catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
		validate(restaurante, "restaurante");
		return restauranteRepository.save(restaurante);
	}
	
	@Transactional
	public void removeFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findById(restauranteId);
		FormaPagamento formasPagamento = formaPagamentoService.findById(formaPagamentoId);
		restaurante.getFormasPagamento().remove(formasPagamento);
	}
	
	@Transactional
	public List<FormaPagamento> adicionaFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = findById(restauranteId);
		FormaPagamento formasPagamento = formaPagamentoService.findById(formaPagamentoId);
		restaurante.getFormasPagamento().add(formasPagamento);
		return formaPagamentoService.findAll();
	}
	
	@Transactional
	public void ativacao(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.setAtivo(true);
	}
	
	@Transactional
	public void ativacaoEmMassa(List<Long> ids) {
		ids.forEach(this::ativacao);
	}
	
	@Transactional
	public void inativacao(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.setAtivo(false);
	}
	
	@Transactional
	public void inativacaoEmMassa(List<Long> ids) {
		ids.forEach(this::inativacao);
	}
	
	@Transactional
	public void abrirRestaurante(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.setAberto(true);
	}
	
	@Transactional
	public void fecharRestaurante(Long id) {
		Restaurante restaurante = findById(id);
		restaurante.setAberto(false);
	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
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

	public Restaurante buscarPrimeiro() {
		return restauranteRepository.buscaPrimeiro()
				.orElseThrow(() -> new RestauranteNaoEncontradoException(""));
	}
}
