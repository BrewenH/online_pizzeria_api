package com.accenture.onlinepizzeriaapi.controller;

import com.accenture.onlinepizzeriaapi.controller.impl.IngredientController;
import com.accenture.onlinepizzeriaapi.exception.IngredientException;
import com.accenture.onlinepizzeriaapi.mapper.IngredientMapper;
import com.accenture.onlinepizzeriaapi.service.IngredientServiceImpl;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientPatchDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import com.accenture.onlinepizzeriaapi.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.bridge.Message;
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
import java.util.UUID;


@WebMvcTest(controllers = IngredientController.class)
class IngredientControllerIntegrationTest {

    private static final String API_INGREDIENTS_ENDPOINT = "/ingredients";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientServiceImpl ingredientService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IngredientMapper ingredientMapper;

    @Test
    @DisplayName("Test to persist new ingredient into database from Controller OK")
    void testAddIngredientSuccess() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Mozzarella";
        int quantity = 1;

        IngredientRequestDto jsonBody = new IngredientRequestDto(name, quantity);
        IngredientResponseDto responseDto = new IngredientResponseDto(id, name, quantity);

        Mockito.when(ingredientService.addIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_INGREDIENTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Test to persist new ingredient into database from Controller without JsonBody")
    void testAddIngredientJsonBodyNull() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Mozzarella";
        int quantity = 1;

        IngredientResponseDto responseDto = new IngredientResponseDto(id, name, quantity);

        Mockito.when(ingredientService.addIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post(API_INGREDIENTS_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test find all ingredients OK")
    void testFindAllIngredientSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test find ingredient by its name OK")
    void testFindByNameSuccess() throws Exception {

        UUID id = UUID.randomUUID();
        String name = "Mozzarella";
        int quantity = 1;

        IngredientResponseDto responseDto = new IngredientResponseDto(id, name, quantity);

        Mockito.when(ingredientService.findByName(name)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT + "/" + name))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test find ingredient invalid with name not found")
    void testFindByNameFail() throws Exception {

        Mockito.when(ingredientService.findByName(" ")).thenThrow(new EntityNotFoundException(Messages.INGREDIENT_NAME_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.get(API_INGREDIENTS_ENDPOINT + "/ "))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Test modify ingredient quantity with valid name and quantity")
    void testPatchQuantityOk() throws Exception {

        UUID id = UUID.randomUUID();
        String name = "Mozzarella";
        int quantity = 5;

        IngredientPatchDto jsonBody = new IngredientPatchDto(quantity);
        IngredientResponseDto responseDto = new IngredientResponseDto(id, name, quantity);

        Mockito.when(ingredientService.patchIngredient((Mockito.any(String.class)), Mockito.any(IngredientPatchDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(API_INGREDIENTS_ENDPOINT + "/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test modify ingredient quantity with invalid name")
    void testPatchQuantityNameNotFound() throws Exception {

        String name = "Mozzarella";

        IngredientPatchDto jsonBody = new IngredientPatchDto(3);

        Mockito.when(ingredientService.patchIngredient((Mockito.any(String.class)), Mockito.any(IngredientPatchDto.class))).thenThrow(new IngredientException(Messages.INGREDIENT_NAME_NOT_FOUND));

        mockMvc.perform(MockMvcRequestBuilders.patch(API_INGREDIENTS_ENDPOINT + "/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(jsonBody)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test modify ingredient quantity with no dto")
    void testPatchQuantityJsonNull() throws Exception {

        UUID id = UUID.randomUUID();
        String name = "Mozzarella";
        int quantity = 5;

        IngredientResponseDto responseDto = new IngredientResponseDto(id, name, quantity);

        Mockito.when(ingredientService.patchIngredient((Mockito.any(String.class)), Mockito.any(IngredientPatchDto.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.patch(API_INGREDIENTS_ENDPOINT + "/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}