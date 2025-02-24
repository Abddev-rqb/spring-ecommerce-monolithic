package com.ecommerce.project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean //This is created as bean to use in future addons to the DB
    public ModelMapper modelMapper(){
        return new ModelMapper(); // returns new modelMappers builds a new connections
    }
}
