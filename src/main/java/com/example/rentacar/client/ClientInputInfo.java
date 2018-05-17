package com.example.rentacar.client;

import io.swagger.annotations.ApiModelProperty;

public class ClientInputInfo {
    @ApiModelProperty(value = "Client", notes = "Name of the client", example = "client")
    private String name;
    @ApiModelProperty(value = "1999", notes = "Birth year of the client", example = "1990")
    private Integer birthYear;
    @ApiModelProperty(notes = "Car's model year", example = "1990")
    private Integer carYear;
    @ApiModelProperty(notes = "Name of the model", example = "bmw")
    private String model;

    public ClientInputInfo() {
    }

    public ClientInputInfo(String name, Integer birthYear, String model, Integer carYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.model = model;
        this.carYear = carYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getCarYear() {
        return carYear;
    }

    public void setCarYear(Integer carYear) {
        this.carYear = carYear;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
