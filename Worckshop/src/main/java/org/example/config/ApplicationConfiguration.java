package org.example.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfiguration {
    @Bean(name = "default")
    @Primary
    public ModelMapper createMapper() {
        return new ModelMapper();
    }

    @Bean(name = "forObjectsWithDates")
    public ModelMapper createOtherMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                String.class, LocalDate.class);
        return modelMapper;
    }

    @Bean
    public Gson createGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
