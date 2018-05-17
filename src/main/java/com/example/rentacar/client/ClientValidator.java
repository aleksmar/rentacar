package com.example.rentacar.client;

import org.springframework.stereotype.Component;

@Component
public class ClientValidator {
    private final ClientRepository repository;

    boolean isClientExists(String clientName) {
        return repository.findByName(clientName).isPresent();
    }

    public ClientValidator(ClientRepository repository) {
        this.repository = repository;
    }
}
