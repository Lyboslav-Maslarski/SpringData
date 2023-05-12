package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDTO;
import com.example.football.models.dto.StatWrapperDTO;
import com.example.football.models.dto.TownImportDTO;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Town;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {
    public static final String STATS_XML = "src/main/resources/files/xml/stats.xml";
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public StatServiceImpl(StatRepository statRepository, ModelMapper modelMapper, Validator validator) {
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(Path.of(STATS_XML));
    }

    @Override
    public String importStats() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(StatWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StatWrapperDTO statWrapperDTO = (StatWrapperDTO) unmarshaller.unmarshal(new FileReader(STATS_XML));
        return statWrapperDTO
                .getList()
                .stream()
                .map(this::importStat)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importStat(StatImportDTO statImportDTO) {
        Set<ConstraintViolation<StatImportDTO>> validate = validator.validate(statImportDTO);
        if (!validate.isEmpty()) {
            return "Invalid Stat";
        }

        if (this.statRepository
                        .findByShootingAndPassingAndEndurance(statImportDTO.getShooting(),
                                statImportDTO.getPassing(), statImportDTO.getEndurance())
                        .isPresent()) {
            return "Invalid Stat";
        }

        Stat stat = modelMapper.map(statImportDTO, Stat.class);
        statRepository.save(stat);

        return String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                stat.getPassing(), stat.getShooting(), stat.getEndurance());
    }
}
