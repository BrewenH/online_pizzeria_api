package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.enums.Size;
import com.accenture.onlinepizzeriaapi.service.PizzaServiceImpl;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class PizzaControllerIntegrationEndToEndTest {

    private static final String API_PIZZAS_ENDPOINT = "/pizzas";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    PizzaServiceImpl pizzaService;

    @Test
    @Order(1)
    @DisplayName("Creates a Pizza through Post endpoint")
    void testPostPizzaSuccess() {

        String name = "Margherita";
        Size size = Size.MEDIUM;
        Double price = 10.50;
        List<Ingredient> ingredients = List.of();
        Boolean removedFromMenu = false;

        PizzaRequestDto pizzaRequestDto = new PizzaRequestDto(name, size, price, ingredients, removedFromMenu);

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:" + port + API_PIZZAS_ENDPOINT, pizzaRequestDto, Void.class);

        PizzaResponseDto pizzaResponseDto = pizzaService.addPizza(pizzaRequestDto);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "Post pizza must return a 201 http response code");
            Assertions.assertNotNull(pizzaResponseDto, "Pizza dto response returned from service must not be null");
            Assertions.assertNotNull(pizzaResponseDto.id(), "Pizza return must not have a null UUID");
            Assertions.assertEquals(name, pizzaResponseDto.name(), "Pizza name must match the request name");
            Assertions.assertEquals(size, pizzaResponseDto.size(), "Pizza size must match the request size");
            Assertions.assertEquals(price, pizzaResponseDto.price(), "Pizza price must match the request price");
            Assertions.assertEquals(ingredients, pizzaResponseDto.ingredients(), "Pizza ingredients must match the request naingredientsme");
            Assertions.assertEquals(removedFromMenu, pizzaResponseDto.removedFromMenu(), "Pizza removedFromMenu must match the request removedFromMenu");
        });
    }

    @Test
    @Order(2)
    @DisplayName("Test the find all get endpoint")
    void testGetAllPizzaSuccess() {
        ResponseEntity<List<PizzaResponseDto>> responseEntity = restTemplate.exchange("http://localhost:" + port + API_PIZZAS_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        List<PizzaResponseDto> pizzas = responseEntity.getBody();
        System.err.println("response body : " + responseEntity.getBody());
        List<Ingredient> ingredients = List.of();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assertions.assertNotNull(responseEntity.getBody());
            Assertions.assertEquals("Margherita", pizzas.getFirst().name());
            Assertions.assertEquals(Size.MEDIUM, pizzas.getFirst().size());
            Assertions.assertEquals(10.50, pizzas.getFirst().price());
            Assertions.assertEquals(ingredients, pizzas.getFirst().ingredients());
            Assertions.assertEquals(false, pizzas.getFirst().removedFromMenu());
        });
    }
}
