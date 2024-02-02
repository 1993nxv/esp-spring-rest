package com.algaworks.algafood.api.exceptionhendler;

import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof IgnoredPropertyException) {
			return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
		}else if(rootCause instanceof UnrecognizedPropertyException) {
			return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.CORPO_INVALIDO;
		String detail = "JSON parse error, corpo enviado é inválido, verifique a sintaxe";
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
		
	}
	
//	protected ResponseEntity<Object> handleHttpMessageNotReadable(PropertyBindingException e,
//			HttpHeaders headers, HttpStatus status, WebRequest request) {
//		
//		Throwable rootCause = ExceptionUtils.getRootCause(e);
//		
//		if(rootCause instanceof IgnoredPropertyException) {
//			return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
//		}else if(rootCause instanceof UnrecognizedPropertyException) {
//			return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
//		}
//		
//		ProblemType problemType = ProblemType.CORPO_INVALIDO;
//		String detail = "JSON parse error, corpo enviado é inválido, verifique a sintaxe";
//		
//		Problem problem = createProblemBuilder(status, problemType, detail).build();
//		
//		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
//		
//	}
//	
	
	private ResponseEntity<Object> handleInvalidFormatExcepion(InvalidFormatException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = e.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
				ProblemType problemType = ProblemType.CORPO_INVALIDO;
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', " 
				+ "que é um tipo inválido."
				+ " Corrija e informe um valor compatível com o tipo %s.",
				path, e.getValue(), e.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.NEGOCIO_EXCEPTION;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT	;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage()).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}
