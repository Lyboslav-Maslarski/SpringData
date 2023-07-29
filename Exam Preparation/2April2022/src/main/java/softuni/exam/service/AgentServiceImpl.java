package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {
    private final AgentRepository agentRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, Gson gson,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/agents.json"));
    }

    @Override
    public String importAgents() throws IOException {
        AgentImportDTO[] agentImportDTOS = gson.fromJson(readAgentsFromFile(), AgentImportDTO[].class);

        return Arrays.stream(agentImportDTOS)
                .map(this::importAgent)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importAgent(AgentImportDTO agentImportDTO) {
        if (!validationUtil.isValid(agentImportDTO) ||
                agentRepository.findByFirstName(agentImportDTO.getFirstName()).isPresent() ||
                agentRepository.findByEmail(agentImportDTO.getEmail()).isPresent()) {
            return "Invalid agent";
        }

        Optional<Town> town = townRepository.findByTownName(agentImportDTO.getTown());
        if (town.isEmpty()) {
            return "Invalid agent";
        }

        Agent agent = modelMapper.map(agentImportDTO, Agent.class);
        agent.setTown(town.get());
        agentRepository.save(agent);

        return String.format("Successfully imported agent - %s %s", agent.getFirstName(), agent.getLastName());
    }
}
