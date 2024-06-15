package com.algaworks.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> tipos;
	
	@Override
	public void initialize(FileContentType constraintAnnotation) {
		this.tipos = Arrays.asList(constraintAnnotation.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		return tipos.stream().anyMatch(tipo -> value.getContentType().contains(tipo));
	}	
}
