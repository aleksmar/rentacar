package com.example.rentacar.car;

import com.example.rentacar.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CarValidator.class)
public class CarValidatorTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarValidator carValidator;

    private static final String TEST_MODEL = "model-x";
    private static final String TEST_NAME = "mike";

    @Test
    public void whenCarIsNotRented_thenReturnFalse() {
        Car testResult = new Car(TEST_MODEL, 2018);
        testResult.setId(1L);

        when(carRepository.findByModel(TEST_MODEL))
                .thenReturn(Optional.of(testResult));

        assertFalse(carValidator.isRentedByClient(TEST_MODEL, TEST_NAME));
    }

    @Test
    public void whenCarIsRented_thenReturnTrue() {
        Car testResult = new Car(TEST_MODEL, 2018);
        testResult.setId(1L);
        testResult.setClient(new Client(TEST_NAME, 1999));

        when(carRepository.findByModel(TEST_MODEL))
                .thenReturn(Optional.of(testResult));

        assertTrue(carValidator.isRentedByClient(TEST_MODEL, TEST_NAME));
    }

    @Test
    public void whenNoCar_thenReturnFalse() {
        assertFalse(carValidator.isCarExists("model-x"));
    }

    @Test
    public void whenCarExists_thenReturnTrue() {
        Car testResult = new Car(TEST_MODEL, 2018);
        testResult.setId(1L);

        when(carRepository.findByModel(TEST_MODEL))
                .thenReturn(Optional.of(testResult));

        assertTrue(carValidator.isCarExists(TEST_MODEL));
    }
}