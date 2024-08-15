package com.algaworks.algafood.api.openapi.controller;

import org.springframework.context.annotation.Import;

import io.swagger.annotations.Api;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

@Api(tags = "Produtos")
@Import(BeanValidatorPluginsConfiguration.class)
public interface RestauranteProdutoControllerOpenApi {
	
}
