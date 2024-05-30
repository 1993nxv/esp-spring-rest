package com.algaworks.algafood.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageJsonSerializer<T> extends JsonSerializer<Page<T>>{

	@Override
	public void serialize(Page<T> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		gen.writeStartObject();
		
		gen.writeObjectField("content", page.getContent());
		gen.writeObjectField("size", page.getSize());
		gen.writeObjectField("totalElements", page.getTotalElements());
		gen.writeObjectField("page", page.getNumber());
		gen.writeObjectField("pages", page.getTotalPages());
		
		gen.writeEndObject();
	}
	
}
