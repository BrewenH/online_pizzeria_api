package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.mapper.IngredientMapper;
import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.repository.IngredientDao;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.IngredientResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    private IngredientDao ingredientDao;

    @Mock
    private IngredientMapper ingredientMapper;

    @Mock
    private MessageSourceAccessor messages;

    private IngredientService service;

    @BeforeEach
    void setUp() {
        ingredientDao = mock(IngredientDao.class);
        ingredientMapper = mock(IngredientMapper.class);
        messages = mock(MessageSourceAccessor.class);
        service = new IngredientServiceImpl(ingredientDao, ingredientMapper, messages);
    }

    @Nested
    @DisplayName("Add a new ingredient")
    class AddIngredientTests {

        @Test
        @DisplayName("Add ingredient with valid input")
        void testAddIngredientSuccess() {

            IngredientService spy = Mockito.spy(service);

            String name = "Mozzarella";
            int quantity = 1;

            IngredientRequestDto requestDto = new IngredientRequestDto(name, quantity);
            Ingredient ingredientEntity = new Ingredient(name, quantity);
            UUID id = UUID.randomUUID();
            IngredientResponseDto expectedResponseDto = new IngredientResponseDto(id, name, quantity);


            Mockito.when(ingredientMapper.toIngredient(Mockito.any(IngredientRequestDto.class))).thenReturn(ingredientEntity);
            Mockito.when(ingredientDao.save(Mockito.any(Ingredient.class))).thenReturn(ingredientEntity);
            Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expectedResponseDto);

            IngredientResponseDto returnedResponseDto = spy.addIngredient(requestDto);

            Assertions.assertAll(
                    () -> Assertions.assertNotNull(returnedResponseDto, "Dto response should not be null"),
                    () -> Assertions.assertNotNull(returnedResponseDto.id(), "Id should not be null"),
                    () -> Assertions.assertNotNull(returnedResponseDto.name(), "Name should not be null"),
                    () -> Assertions.assertTrue(returnedResponseDto.quantity() >= 0, "Quantity should not be negative"),
                    () -> Assertions.assertEquals(id, returnedResponseDto.id(), "Id should be the same as expected"),
                    () -> Assertions.assertEquals(name, returnedResponseDto.name(), "Name should be the same as expected"),
                    () -> Assertions.assertEquals(quantity, returnedResponseDto.quantity(), "Quantity should be the same as expected")

            );

            Mockito.verify(spy, Mockito.times(1)).checkIngredient(Mockito.any(IngredientRequestDto.class));
        }
    }
}