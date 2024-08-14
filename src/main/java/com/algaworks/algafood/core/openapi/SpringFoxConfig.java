package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.ParameterBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhendler.Problem;
import com.algaworks.algafood.api.openapi.model.CozinhasModelOpenApi;
import com.algaworks.algafood.api.openapi.model.PageableModelOpenApi;
import com.algaworks.algafood.domain.model.modelDTO.CozinhaDTO;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {
	
	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
//				.paths(PathSelectors.ant("/restaurantes/*"))
				.build()
			.apiInfo(apiInfo())
			.additionalModels(typeResolver.resolve(Problem.class))
			.directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, CozinhaDTO.class), 
					CozinhasModelOpenApi.class)
			)
			.ignoredParameterTypes(ServletWebRequest.class)
			.useDefaultResponseMessages(false)
			.globalOperationParameters(Arrays.asList(
					new ParameterBuilder()
						.name("campos")
						.description("Nomes das propriedades para filtrar na resposta, separados por vírgula")
						.parameterType("query")
						.modelRef(new ModelRef("String"))
						.build()
			))
			.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
			.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
			.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
			.globalResponseMessage(RequestMethod.PATCH, globalPostPutResponseMessages())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
			.tags(new Tag("Cidades", "Gerencia as cidades"))
			.tags(new Tag("Grupos", "Gerencia os grupos de usuários"))
			.tags(new Tag("Cozinhas", "Gerencia as cozinhas"));
	}
	
	private List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_NOT_ACCEPTABLE)
					.message("Recurso não possui representação aceita pelo consumidor")
					.build()
		);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_BAD_REQUEST)
					.message("Requisição inválida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_NOT_ACCEPTABLE)
					.message("Recurso não possui representação aceita pelo consumidor")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE)
					.message("Requisição recusada porque o corpo está em um formato não suportado")
					.responseModel(new ModelRef("Problema"))
					.build()
		);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.message("Erro interno do servidor")
					.responseModel(new ModelRef("Problema"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.SC_BAD_REQUEST)
					.message("Requisição inválida (erro do cliente)")
					.responseModel(new ModelRef("Problema"))
					.build()
		);
	}
	
	private  ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("AlgaFood API")
				.description("API aberta para clientes e restaurantes")
				.version("1")
				.contact(new Contact("AlgaWorks", "https://www.algaworks", "contato@algaworks.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
