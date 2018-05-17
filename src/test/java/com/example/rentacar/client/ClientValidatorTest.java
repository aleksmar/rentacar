package com.example.rentacar.client;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientValidator.class)
public class ClientValidatorTest {

    @MockBean
    private ClientRepository repository;

    @Autowired
    private ClientValidator validator;

    @Test
    public void whenUserExists_returnFalse() {
        String username = "alex";
        Client testClient = new Client();
        testClient.setId(1L);
        testClient.setName(username);
        testClient.setBirthYear(1985);
        ClientInputInfo testInfo = new ClientInputInfo();
        testInfo.setName(username);

        when(repository.findByName(username)).thenReturn(Optional.of(testClient));

        assertTrue(validator.isClientExists(testInfo.getName()));
    }

}