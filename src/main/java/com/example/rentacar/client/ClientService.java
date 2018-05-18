package com.example.rentacar.client;

import com.example.rentacar.car.Car;
import com.example.rentacar.car.CarRepository;
import com.example.rentacar.client.Client;
import com.example.rentacar.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService {

    private final CarRepository carRepository;
    private final ClientRepository clientRepository;

    public void rentCar(String name, Integer birthYear, String model) {
        Car car = carRepository.findByModel(model)
                .orElseThrow(() -> new IllegalStateException("Missing car: " + model));
        Client client = new Client(name, birthYear, car);
        Client newClient = clientRepository.save(client);
        car.setClient(newClient);
        carRepository.save(car);
    }

    public void removeClient(String name) {
        Client client = clientRepository.findByName(name).get();
        Car car = client.getCar();
        car.setClient(null);
        carRepository.save(car);
        clientRepository.delete(client);
    }

    public ClientService(CarRepository carRepository, ClientRepository clientRepository) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }
}
