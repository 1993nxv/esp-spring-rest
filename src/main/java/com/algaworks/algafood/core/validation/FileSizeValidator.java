package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
	}
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		return valido;
	}	
}
