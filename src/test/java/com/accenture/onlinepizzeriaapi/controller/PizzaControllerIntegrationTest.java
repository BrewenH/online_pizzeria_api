package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.controller.impl.PizzaController;
import com.accenture.onlinepizzeriaapi.mapper.PizzaMapper;
import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.enums.Size;
import com.accenture.onlinepizzeriaapi.service.PizzaServiceImpl;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@WebMvcTest(controllers = PizzaController.class)
 class PizzaControllerIntegrationTest {

    private static final String API_PIZZAS_ENDPOINT = "/pizzas";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PizzaServiceImpl pizzaService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PizzaMapper pizzaMapper;

    @Test
    @DisplayName("Test to perist pizza into the postgres database")
    void testPersistPizzaSuccess() throws Exception {

        String name = "Margherita";
        Size size = Size.MEDIUM;
        Double price = 10.50;
        List<Ingredient> ingredients = List.of();
        Boolean removedFromMenu = false;
        PizzaRequestDto requestDto = new PizzaRequestDto(name, size, price, ingredients, removedFromMenu);

        PizzaResponseDto responseDto = new PizzaResponseDto(UUID.randomUUID(), name, size, price, ingredients, removedFromMenu);

        Mockito.when(pizzaService.addPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PIZZAS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Test to persist new pizza into database from Controller without JsonBody")
    void testAddPizzaJsonBodyNull() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Margherita";
        Size size = Size.MEDIUM;
        Double price = 10.50;
        List<Ingredient> ingredients = List.of();
        Boolean removedFromMenu = false;

        PizzaResponseDto responseDto = new PizzaResponseDto(id, name, size, price, ingredients, removedFromMenu);

        Mockito.when(pizzaService.addPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_PIZZAS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test get all pizzas with success")
    void testFindAllPizzasSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(API_PIZZAS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
