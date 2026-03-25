package com.accenture.onlinepizzeriaapi.service;

import com.accenture.onlinepizzeriaapi.exception.PizzaException;
import com.accenture.onlinepizzeriaapi.mapper.PizzaMapper;
import com.accenture.onlinepizzeriaapi.model.Ingredient;
import com.accenture.onlinepizzeriaapi.model.Pizza;
import com.accenture.onlinepizzeriaapi.model.enums.Size;
import com.accenture.onlinepizzeriaapi.repository.PizzaDao;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaRequestDto;
import com.accenture.onlinepizzeriaapi.service.dto.PizzaResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.MessageSourceAccessor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PizzaServiceImplTest {

    @Mock
    private PizzaDao pizzaDao;
    @Mock
    private PizzaMapper pizzaMapper;
    @Mock
    private MessageSourceAccessor messages;
    private PizzaService service;

    @BeforeEach
    void setUp() {
        pizzaDao = mock(PizzaDao.class);
        pizzaMapper = mock(PizzaMapper.class);
        messages = mock(MessageSourceAccessor.class);
        service = new PizzaServiceImpl(pizzaDao, pizzaMapper, messages);

    }

    @Nested
    @DisplayName("addPizza")
    class addPizzaTest {


        @Test
        @DisplayName("OK")
        void addPizzaOk() {

            PizzaService spy = Mockito.spy(service);
            String name = "Margherita";
            Size size = Size.MEDIUM;
            Double price = 12.90;
            List<Ingredient> ingredients = List.of();
            Boolean removedFromMenu = false;
            PizzaRequestDto dtoRequest = new PizzaRequestDto(name, size, price, ingredients, removedFromMenu);
            PizzaResponseDto returnResponse = new PizzaResponseDto(UUID.randomUUID(), name, Size.MEDIUM, price, ingredients, removedFromMenu);
            Pizza pizzaEntity = new Pizza(name, Size.MEDIUM, price, ingredients, removedFromMenu);

            Mockito.when(pizzaMapper.toPizza(Mockito.any(PizzaRequestDto.class))).thenReturn(pizzaEntity);
            Mockito.when(pizzaDao.save(Mockito.any(Pizza.class))).thenReturn(pizzaEntity);
            Mockito.when(pizzaMapper.toPizzaResponseDto(Mockito.any(Pizza.class))).thenReturn(returnResponse);

            PizzaResponseDto returnedValue = spy.addPizza(dtoRequest);


            Assertions.assertAll(
                    () -> Assertions.assertNotNull(returnedValue, "DtoResponse should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.id(), "Id should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.name(), "Name should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.size(), "Size should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.price(), "Price should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.ingredients(), "Ingredients should not be null"),
                    () -> Assertions.assertNotNull(returnedValue.removedFromMenu(), "Removed from Menu should not be null"),
                    () -> Assertions.assertEquals(name, returnedValue.name(), "Name should be the same as the expected"),
                    () -> Assertions.assertEquals(size, returnedValue.size(), "Size should be the same as the expected"),
                    () -> Assertions.assertEquals(price, returnedValue.price(), "Price should be the same as the expected"),
                    () -> Assertions.assertEquals(ingredients, returnedValue.ingredients(), "Ingredients should be the same as the expected"),
                    () -> Assertions.assertEquals(removedFromMenu, returnedValue.removedFromMenu(), "RemovedFromMenu should be the same as the expected")
            );
            Mockito.verify(spy, Mockito.times(1)).checkPizza(Mockito.any(PizzaRequestDto.class));

        }

        @Test
        @DisplayName("Invalid (null)")
        void addPizzaInvalidNull() {
            assertThrows(PizzaException.class, () -> service.addPizza(null));
        }
    }

    @Nested
    @DisplayName("findPizza")
    class findPizzaTest {

        @Test
        @DisplayName("all : OK")
        void findAllValidTest() {

            String name = "Margherita";
            Size size = Size.MEDIUM;
            Double price = 10.50;
            List<Ingredient> ingredients = List.of(new Ingredient("tomate", 4));
            Boolean removedFromMenu = false;

            String name1 = "Reine";
            Size size1 = Size.SMALL;
            Double price1 = 12.50;
            Boolean removedFromMenu1 = false;


            Pizza pizza = new Pizza(name, size, price, ingredients, removedFromMenu);
            Pizza pizza1 = new Pizza(name1, size1, price1, ingredients, removedFromMenu1);

            PizzaResponseDto expected = new PizzaResponseDto(UUID.randomUUID(), name, size, price, ingredients, removedFromMenu);
            PizzaResponseDto expected1 = new PizzaResponseDto(UUID.randomUUID(), name1, size1, price1, ingredients, removedFromMenu1);

            Mockito.when(pizzaDao.findAll()).thenReturn(List.of(pizza, pizza1));
            Mockito.when(pizzaMapper.toPizzaResponseDto(pizza)).thenReturn(expected);
            Mockito.when(pizzaMapper.toPizzaResponseDto(pizza1)).thenReturn(expected1);

            List<PizzaResponseDto> returnedResponse = service.findAll();

            Assertions.assertAll(() -> Assertions.assertFalse(returnedResponse.isEmpty(), "DtoResponse should not be null"),
                    () -> Assertions.assertEquals(2, returnedResponse.size(), "Response size should be the same as expected"),
                    () -> Assertions.assertEquals(expected.id(), returnedResponse.getFirst().id(), "Id should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.id(), returnedResponse.get(1).id(), "Id should be the same as expected"),
                    () -> Assertions.assertEquals(expected.name(), returnedResponse.getFirst().name(), "Name should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.name(), returnedResponse.get(1).name(), "Name should be the same as expected"),
                    () -> Assertions.assertEquals(expected.size(), returnedResponse.getFirst().size(), "Size should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.size(), returnedResponse.get(1).size(), "Size should be the same as expected"),
                    () -> Assertions.assertEquals(expected.price(), returnedResponse.getFirst().price(), "Price should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.price(), returnedResponse.get(1).price(), "Price should be the same as expected"),
                    () -> Assertions.assertEquals(expected.ingredients(), returnedResponse.getFirst().ingredients(), "Ingredients should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.ingredients(), returnedResponse.get(1).ingredients(), "Ingredients should be the same as expected"),
                    () -> Assertions.assertEquals(expected.removedFromMenu(), returnedResponse.getFirst().removedFromMenu(), "RemovedFromMenu should be the same as expected"),
                    () -> Assertions.assertEquals(expected1.removedFromMenu(), returnedResponse.get(1).removedFromMenu(), "RemovedFromMenu should be the same as expected"));


        }

        @Test
        @DisplayName("Test method findAll from service, must return correct output")
        void findByIdValidTest() {
            UUID inputId = UUID.randomUUID();
            String name = "Margherita";
            Size size = Size.MEDIUM;
            Double price = 12.90;
            List<Ingredient> ingredients = List.of();
            Boolean removedFromMenu = false;

            Pizza originalPizza = new Pizza(name, size, price, ingredients, removedFromMenu);
            originalPizza.setId(inputId);

            PizzaResponseDto expectedResponse = new PizzaResponseDto(inputId, name, size, price, ingredients, removedFromMenu);

            Mockito.when(pizzaDao.getReferenceById(Mockito.any(UUID.class))).thenReturn(originalPizza);

            Mockito.when(pizzaMapper.toPizzaResponseDto(Mockito.any(Pizza.class))).thenReturn(expectedResponse);

            PizzaResponseDto returnedResponse = service.findById(inputId);

            Assertions.assertAll(() -> Assertions.assertNotNull(returnedResponse, "DtoResponse should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.id(), "Id should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.name(), "Name should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.size(), "Size should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.price(), "Price should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.ingredients(), "Ingredients should not be null"),
                    () -> Assertions.assertNotNull(returnedResponse.removedFromMenu(), "Removed from Menu should not be null"),
                    () -> Assertions.assertEquals(name, returnedResponse.name(), "Name should be the same as the expected"),
                    () -> Assertions.assertEquals(size, returnedResponse.size(), "Size should be the same as the expected"),
                    () -> Assertions.assertEquals(price, returnedResponse.price(), "Price should be the same as the expected"),
                    () -> Assertions.assertEquals(ingredients, returnedResponse.ingredients(), "Ingredients should be the same as the expected"),
                    () -> Assertions.assertEquals(removedFromMenu, returnedResponse.removedFromMenu(), "RemovedFromMenu should be the same as the expected"));
        }

        @Test
        @DisplayName("Test method findByName from service, must return correct output")
        void findByNameValidTest() {

            String name = "Margherita";
            Size size = Size.MEDIUM;
            Double price = 12.90;
            List<Ingredient> ingredients = List.of();
            Boolean removedFromMenu = false;

            Pizza originalPizza = new Pizza(name, size, price, ingredients, removedFromMenu);
            originalPizza.setName(name);

            Mockito.when(pizzaDao.findByName("Margherita")).thenReturn(Optional.of(originalPizza));

            Optional<Pizza> returnedResponse = pizzaDao.findByName(name);

            Assertions.assertNotNull(returnedResponse);
            Assertions.assertEquals("Margherita", returnedResponse.get().getName());
        }


    }


}