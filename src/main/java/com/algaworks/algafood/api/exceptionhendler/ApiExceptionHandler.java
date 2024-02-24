package com.algaworks.algafood.api.exceptionhendler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICO = "Ocorreu um erro interno inesperado no sistema."
			+ "Tente novamente e se o problema persisitir, entre em "
			+ "contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	private ResponseEntity<Object> handleExcepion(Exception e, WebRequest request) {
		
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		String detail = MSG_ERRO_GENERICO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		e.printStackTrace();
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		BindingResult bindingResult = e.getBindingResult();
		
		List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
				.map(fieldError -> {
					
					String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
					
					return	Problem.Field.builder()
							.name(fieldError.getObjectName()+"."+fieldError.getField())
							.userMessage(message)
							.build();
				})
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.fields(problemFields)
				.build();
				
		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		    
		if (rootCause instanceof InvalidFormatException) {
		    	return handleInvalidFormatExcepion((InvalidFormatException) rootCause, headers, status, request);
	    } else if (rootCause instanceof PropertyBindingException) {
		    	return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request); 
		}
		    
		ProblemType problemType = ProblemType.CORPO_INVALIDO;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		    
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO)
				.build();
		    
		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatExcepion(InvalidFormatException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String path = joinPath(e.getPath());
		
		ProblemType problemType = ProblemType.CORPO_INVALIDO;
		
		String detail = String.format("A propriedade '%s' recebeu o valor '%s', " 
				+ "que é um tipo inválido."
				+ " Corrija e informe um valor compatível com o tipo %s.",
				path, e.getValue(), e.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICO)
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException e,
	        HttpHeaders headers, HttpStatus status, WebRequest request) {

	    String path = joinPath(e.getPath());
	    
	    ProblemType problemType = ProblemType.CORPO_INVALIDO;
	    String detail = String.format("A propriedade '%s' não existe. "
	            + "Corrija ou remova essa propriedade e tente novamente.", path);

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICO)
	    		.build();
	    
	    return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	private String joinPath(List<Reference> references) {
	    return references.stream()
	        .map(ref -> ref.getFieldName())
	        .collect(Collectors.joining("."));
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {
	    
	    if (e instanceof MethodArgumentTypeMismatchException) {
	        return handleMethodArgumentTypeMismatch(
	                (MethodArgumentTypeMismatchException) e, headers, status, request);
	    }

	    return super.handleTypeMismatch(e, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
	        MethodArgumentTypeMismatchException e, HttpHeaders headers,
	        HttpStatus status, WebRequest request) {

	    ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

	    String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
	            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
	            e.getName(), e.getValue(), e.getRequiredType().getSimpleName());

	    Problem problem = createProblemBuilder(status, problemType, detail)
	    		.userMessage(MSG_ERRO_GENERICO)
	    		.build();

	    return handleExceptionInternal(e, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage())
				.userMessage(e.getMessage())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.NEGOCIO_EXCEPTION;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage())
				.userMessage(e.getMessage())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT	;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, e.getMessage())
				.userMessage(e.getMessage())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (body == null) {
			body = Problem.builder()
					.timeStamp(LocalDateTime.now())
					.title(status.getReasonPhrase())
					.status(status.value())
					.userMessage(MSG_ERRO_GENERICO)
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.timeStamp(LocalDateTime.now())
					.title((String) body)
					.status(status.value())
					.userMessage(MSG_ERRO_GENERICO)
					.build();
		}
		return super.handleExceptionInternal(e, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		return Problem.builder()
				.timeStamp(LocalDateTime.now())
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}
