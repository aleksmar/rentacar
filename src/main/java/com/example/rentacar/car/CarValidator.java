package com.example.rentacar.car;

import com.example.rentacar.client.Client;
import org.springframework.stereotype.Component;

@Component
public class CarValidator {
    private final CarRepository repository;

    public boolean isCarExists(String model) {
        return repository.findByModel(model).isPresent();
    }

    public boolean isRentedByClient(String model, String name) {
        Car car = repository.findByModel(model)
                .orElseThrow(() -> new IllegalStateException("Missing model: " + model));

        Client client = car.getClient();
        return client != null && name.equals(client.getName());
    }

    public CarValidator(CarRepository repository) {
        this.repository = repository;
    }
}
