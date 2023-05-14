package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TownServiceImpl implements TownService {
    public static final String TOWNS_JSON = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, Validator validator) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWNS_JSON));
    }

    @Override
    public String importTowns() throws IOException {
        TownImportDTO[] townImportDTOS = this.gson.fromJson(readTownsFileContent(), TownImportDTO[].class);
        return Arrays.stream(townImportDTOS)
                .map(this::importTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTown(TownImportDTO townImportDTO) {
        Set<ConstraintViolation<TownImportDTO>> validate = validator.validate(townImportDTO);
        if (!validate.isEmpty()) {
            return "Invalid Town";
        }

        if (this.townRepository.findByName(townImportDTO.getName()).isPresent()) {
            return "Invalid Town";
        }

        Town town = modelMapper.map(townImportDTO, Town.class);
        townRepository.save(town);

        return String.format("Successfully imported Town %s - %d", town.getName(), town.getPopulation());
    }
}
