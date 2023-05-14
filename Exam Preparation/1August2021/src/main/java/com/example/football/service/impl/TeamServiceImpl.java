package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    public static final String TEAMS_JSON = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ModelMapper modelMapper, Gson gson, Validator validator) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAMS_JSON));
    }

    @Override
    public String importTeams() throws IOException {
        TeamImportDTO[] townImportDTOS = this.gson.fromJson(readTeamsFileContent(), TeamImportDTO[].class);
        return Arrays.stream(townImportDTOS)
                .map(this::importTeam)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTeam(TeamImportDTO teamImportDTO) {
        Set<ConstraintViolation<TeamImportDTO>> validate = validator.validate(teamImportDTO);
        if (!validate.isEmpty()) {
            return "Invalid Team";
        }

        if (this.teamRepository.findByName(teamImportDTO.getName()).isPresent()) {
            return "Invalid Team";
        }

        Team team = modelMapper.map(teamImportDTO, Team.class);

        Town town = townRepository.findByName(teamImportDTO.getTownName()).get();
        team.setTown(town);

        teamRepository.save(team);

        return String.format("Successfully imported Team %s - %d", team.getName(), team.getFanBase());
    }
}
