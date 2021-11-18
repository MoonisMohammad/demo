package com.example.demo.facility;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FacilityConfig {

    @Bean
    CommandLineRunner commandLineRunner(FacilityRepository repository){
        return args -> {
            Facility f1 = new Facility(
                                        Long.valueOf("1"),
                                        "Carleton court",
                                        "Carleton"
                                        );
            Facility f2 = new Facility(
                                        Long.valueOf("2"),
                                        "Ottawa court",
                                        "Ottawa park"
                                        );
            Facility f3 = new Facility(
                                        Long.valueOf("1"),
                                        "Ottawa court",
                                        "Ottawa park"
            );
            repository.saveAll(
                    List.of(f1,f2,f3)
            );
        };
    }
}
