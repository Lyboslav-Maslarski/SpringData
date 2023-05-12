package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;
    private final String COUNTRY_JSON = "src/main/resources/files/json/countries.json";

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, Gson gson, Validator validator) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_JSON));
    }

    @Override
    public String importCountries() throws IOException {
        ImportCountryDTO[] importCountryDTOS = this.gson.fromJson(new FileReader(COUNTRY_JSON), ImportCountryDTO[].class);

        return Arrays.stream(importCountryDTOS)
                .map(this::importCountry)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCountry(ImportCountryDTO importCountryDTO) {
        Set<ConstraintViolation<ImportCountryDTO>> violations = validator.validate(importCountryDTO);

        if (!violations.isEmpty()) {
            return "Invalid country";
        }
        Optional<Country> optionalCountry = countryRepository.findByCountryName(importCountryDTO.getCountryName());

        if (optionalCountry.isPresent()) {
            return "Invalid country";
        }

        Country country = modelMapper.map(importCountryDTO, Country.class);

        countryRepository.save(country);

        return String.format("Successfully imported country %s - %s", country.getCountryName(), country.getCurrency());
    }
}
