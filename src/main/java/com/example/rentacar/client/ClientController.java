package com.example.rentacar.client;

import com.example.rentacar.car.CarValidator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientValidator clientValidator;
    private final CarValidator carValidator;
    private final ClientService clientService;

    @ApiOperation(value = "addClient", nickname = "addClient")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client was added"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server failure")
    })
    @PostMapping
    public ResponseEntity<String> addClient(@ApiParam
            @RequestBody
            ClientInputInfo info) {
        if (clientValidator.isClientExists(info.getName()))
            return ResponseEntity.badRequest().body("Client " + info.getName() + " already exists");

        if (!carValidator.isCarExists(info.getModel()))
            return ResponseEntity.badRequest().body("No car model: " + info.getModel());

        clientService.rentCar(info.getName(), info.getBirthYear(), info.getModel());

        return ResponseEntity.ok("Client " + info.getName() + " was added.");
    }

    @ApiOperation(value = "removeClient", nickname = "removeClient")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "Client's name", required = true),
            @ApiImplicitParam(name = "model", value = "Model's name", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client was removed"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server failure")
    })
    @DeleteMapping
    public ResponseEntity<String> removeClient(@RequestParam("name") String name,
                                               @RequestParam("model") String model) {

        if (!clientValidator.isClientExists(name))
            return ResponseEntity.badRequest().body("No client with name: " + name);

        if (!carValidator.isRentedByClient(model, name))
            return ResponseEntity.badRequest().body("Model " + model + "is not rented by client " + name);

        clientService.removeClient(name);

        return ResponseEntity.ok("Client " + name + " was removed");
    }

    @Autowired
    public ClientController(ClientValidator clientValidator,
                            CarValidator carValidator,
                            ClientService clientService) {
        this.clientValidator = clientValidator;
        this.carValidator = carValidator;
        this.clientService = clientService;
    }
}
