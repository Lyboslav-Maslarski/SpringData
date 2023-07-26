package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.TownImportDTO;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static hiberspring.common.Constants.*;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    @Autowired
    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(PATH_TO_FILES + "towns.json");
    }

    @Override
    public String importTowns(String townsFileContent) {
        TownImportDTO[] townImportDTOS = gson.fromJson(townsFileContent, TownImportDTO[].class);

        return Arrays.stream(townImportDTOS)
                .map(this::importTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTown(TownImportDTO townImportDTO) {
        if (!validationUtil.isValid(townImportDTO)) {
            return INCORRECT_DATA_MESSAGE;
        }

        Town town = modelMapper.map(townImportDTO, Town.class);

        townRepository.save(town);

        return String.format(SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName());
    }
}
