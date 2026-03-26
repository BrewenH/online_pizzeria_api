package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.service.IngredientServiceImpl;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
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
class IngredientControllerEndToEndTest {

    private static final String API_INGREDIENTS_ENDPOINT = "/ingredients";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    IngredientServiceImpl ingredientService;

    @Test
    @Order(1)
    @DisplayName("Creates an Ingredient through POST endpoint with bad request")
    void testPostIngredientBadRequest() {
        List<IngredientResponseDto> ingredientList = getIngredientResponseDtos();
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_INGREDIENTS_ENDPOINT, null, Void.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Post ingredient must return a 201 http response code.");
            Assertions.assertNotNull(response, "Ingredient dto response returned from service must not be null.");
            Assertions.assertEquals(0, ingredientList.size());
        });
    }
    @Test
    @Order(2)
    @DisplayName("Creates an Ingredient through POST endpoint")
    void testPostIngredientSuccess() {
        String name = "Mozzarella";
        int quantity = 1;

        IngredientRequestDto requestDto = new IngredientRequestDto(name, quantity);
        ResponseEntity<Void> response = restTemplate.postForEntity("http://localhost:" + port + API_INGREDIENTS_ENDPOINT, requestDto, Void.class);
        List<IngredientResponseDto> ingredientList = getIngredientResponseDtos();

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Post ingredient must return a 201 http response code.");
            Assertions.assertNotNull(response, "Ingredient dto response returned from service must not be null.");
            Assertions.assertEquals(1, ingredientList.size());
            Assertions.assertEquals(name, ingredientList.getFirst().name());
            Assertions.assertEquals(quantity, ingredientList.getFirst().quantity());
        });
    }

    @Test
    @Order(3)
    @DisplayName("Test the find all GET endpoint")
    void testGetAllIngredientsSuccess() {
        ResponseEntity<List<IngredientResponseDto>> findAllResponse = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        List<IngredientResponseDto> ingredientList = findAllResponse.getBody();

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, findAllResponse.getStatusCode());
            Assertions.assertNotNull(findAllResponse.getBody());
            Assertions.assertEquals("Mozzarella", ingredientList.getFirst().name());
            Assertions.assertEquals(1, ingredientList.getFirst().quantity());
            Assertions.assertEquals(1, findAllResponse.getBody().size());
        });
    }

    @Test
    @Order(4)
    @DisplayName("Test the find by name through GET/{name} endpoint")
    void testFindByNameSuccess() {
        List<IngredientResponseDto> ingredientLists = getIngredientResponseDtos();

        String name = ingredientLists.getFirst().name();

        ResponseEntity<IngredientResponseDto> findByNameResponse = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINT + "/" + name, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        IngredientResponseDto ingredientResponseDto = findByNameResponse.getBody();

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.OK, findByNameResponse.getStatusCode());
            Assertions.assertNotNull(findByNameResponse.getBody());
            Assertions.assertEquals("Mozzarella", ingredientResponseDto.name());
            Assertions.assertEquals(1, ingredientResponseDto.quantity());
        });
    }

    @Test
    @Order(5)
    @DisplayName("Test the find by name through GET/{name} name not found")
    void testFindByNameBadRequest() {

        String name = "ham";

        ResponseEntity<Void> findByNameResponse = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINT + "/" + name, HttpMethod.GET, null, Void.class);

            Assertions.assertEquals(HttpStatus.NOT_FOUND, findByNameResponse.getStatusCode());
    }

    @Test
    @Order(7)
    @DisplayName("Test the patch quantity method through PATCH/{name} endpoint with bad request")
    void testPatchQuantityBadRequest() {
        List<IngredientResponseDto> ingredientLists = getIngredientResponseDtos();
        String name = ingredientLists.getFirst().name();


        ResponseEntity<Void> patchResponse = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINT + "/" + name, HttpMethod.PATCH, null, Void.class);

        IngredientResponseDto updatedIngredient = ingredientService.findByName(ingredientLists.getFirst().name());

        Assertions.assertAll(() -> {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, patchResponse.getStatusCode());
            Assertions.assertEquals(1, updatedIngredient.quantity());
        });
    }

    private List<IngredientResponseDto> getIngredientResponseDtos() {
        ResponseEntity<List<IngredientResponseDto>> getAllResponse = restTemplate.exchange("http://localhost:" + port + API_INGREDIENTS_ENDPOINT, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        return  getAllResponse.getBody();
    }
}