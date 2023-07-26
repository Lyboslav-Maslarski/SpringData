package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.EmployeeCardImportDTO;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
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
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return fileUtil.readFile(PATH_TO_FILES + "employee-cards.json");
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) {
        EmployeeCardImportDTO[] employeeCardImportDTOS = gson.fromJson(employeeCardsFileContent, EmployeeCardImportDTO[].class);

        return Arrays.stream(employeeCardImportDTOS)
                .map(this::importEmployeeCard)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importEmployeeCard(EmployeeCardImportDTO employeeCardImportDTO) {
        if (!validationUtil.isValid(employeeCardImportDTO)) {
            return INCORRECT_DATA_MESSAGE;
        }

        if (employeeCardRepository.findByNumber(employeeCardImportDTO.getNumber()).isPresent()){
            return INCORRECT_DATA_MESSAGE;
        }

        EmployeeCard employeeCard = modelMapper.map(employeeCardImportDTO, EmployeeCard.class);

        employeeCardRepository.save(employeeCard);

        return String.format(SUCCESSFUL_IMPORT_MESSAGE, employeeCard.getClass().getSimpleName(), employeeCard.getNumber());
    }
}
