package com.example.football.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public ModelMapper createMapper() {
        return new ModelMapper();
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder().create();
    }
    @Bean
    public Validator creteValidator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
