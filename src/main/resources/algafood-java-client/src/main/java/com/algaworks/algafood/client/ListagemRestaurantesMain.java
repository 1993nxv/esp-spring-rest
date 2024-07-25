package com.algaworks.algafood.client;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestauranteClient;

public class ListagemRestaurantesMain {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
		
			RestauranteClient restauranteClient = new RestauranteClient(
					restTemplate, "http://api.algafoods.local:8080");
			
			restauranteClient.listar().stream()
				.forEach(restaurante -> System.out.println(restaurante));
		} catch (ClientApiException e) {
			System.out.println(e.getProblem());
		}
	}
}
