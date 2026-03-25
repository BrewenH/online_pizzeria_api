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
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;
import java.util.UUID;

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

    @Nested
    @DisplayName("Find ingredients methods")
    class FindIngredientTests {

        @Test
        @DisplayName("find all OK")
        void testFindAllSuccess() {
            String name1 = "Mozzarella";
            int quantity1 = 1;
            String name2 = "Ham";
            int quantity2 = 2;

            Ingredient ingredient1 = new Ingredient(name1, quantity1);
            Ingredient ingredient2 = new Ingredient(name2, quantity2);

            IngredientResponseDto expected1 = new IngredientResponseDto(UUID.randomUUID(), name1, quantity1);
            IngredientResponseDto expected2 = new IngredientResponseDto(UUID.randomUUID(), name2, quantity2);

            Mockito.when(ingredientDao.findAll()).thenReturn(List.of(ingredient1, ingredient2));
            Mockito.when(ingredientMapper.toIngredientResponseDto(ingredient1)).thenReturn(expected1);
            Mockito.when(ingredientMapper.toIngredientResponseDto(ingredient2)).thenReturn(expected2);

            List<IngredientResponseDto> returnedResponse = service.findAll();

            Assertions.assertAll(() -> {
                        Assertions.assertFalse(returnedResponse.isEmpty(), "Dto response should not be null");
                        Assertions.assertEquals(2, returnedResponse.size(), "Response size should be the same as expected");
                        Assertions.assertEquals(expected1.id(), returnedResponse.getFirst().id(), "Id should be the same as expected");
                        Assertions.assertEquals(expected2.id(), returnedResponse.get(1).id(), "Id should be the same as expected");
                        Assertions.assertEquals(expected1.name(), returnedResponse.getFirst().name(), "Name should be the same as expected");
                        Assertions.assertEquals(expected2.name(), returnedResponse.get(1).name(), "Name should be the same as expected");
                        Assertions.assertEquals(expected1.quantity(), returnedResponse.getFirst().quantity(), "Quantity should be the same as expected");
                        Assertions.assertEquals(expected2.quantity(), returnedResponse.get(1).quantity(), "Quantity should be the same as expected");
                    }
            );
        }


        @Test
        @DisplayName("findById OK")
        void testFindByIdSuccess() {

            UUID id = UUID.randomUUID();
            String name = "Mozzarella";
            int quantity = 1;
            Ingredient ingredientEntity = new Ingredient(name, quantity);
            IngredientResponseDto expectedResponse = new IngredientResponseDto(id, name, quantity);

            Mockito.when(ingredientDao.getReferenceById(Mockito.any(UUID.class))).thenReturn(ingredientEntity);

            Mockito.when(ingredientMapper.toIngredientResponseDto(Mockito.any(Ingredient.class))).thenReturn(expectedResponse);

            IngredientResponseDto returnedResponse = service.findById(id);

            Assertions.assertAll(() -> {
                Assertions.assertNotNull(returnedResponse, "DtoResponse should not be null");
                Assertions.assertNotNull(returnedResponse.id(), "Id should not be null");
                Assertions.assertNotNull(returnedResponse.name(), "Name should not be null");
                Assertions.assertTrue(returnedResponse.quantity() >= 0, "Quantity should not be negative");
                Assertions.assertEquals(id, returnedResponse.id(), "Id should be the same as expected");
                Assertions.assertEquals(name, returnedResponse.name(), "Name should be the same as expected");
                Assertions.assertEquals(quantity, returnedResponse.quantity(), "Quantity should be the same as expected");
            });
        }
    }
}