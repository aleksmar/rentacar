package com.example.rentacar;

import com.example.rentacar.car.Car;
import com.example.rentacar.car.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
public class RentacarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }


    @Bean
    public CommandLineRunner populator(CarRepository carRepository) {
        return args -> {
            carRepository.save(new Car("mercedes", 2010));
            carRepository.save(new Car("bmw", 2013));
            carRepository.save(new Car("audi", 2014));
            carRepository.save(new Car("lada", 2018));
        };
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(info())
                .select()
                .paths(regex("/clients.*"))
                .build();
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                .title("Rent-a-car REST API")
                .version("2.0")
                .build();
    }
}
