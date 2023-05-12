package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
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
public class CityServiceImpl implements CityService {
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;
    private final String CITY_JSON = "src/main/resources/files/json/city.json";

    @Autowired
    public CityServiceImpl(CountryRepository countryRepository, CityRepository cityRepository, ModelMapper modelMapper, Gson gson, Validator validator) {
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(Path.of(CITY_JSON));
    }

    @Override
    public String importCities() throws IOException {
        ImportCityDTO[] importCityDTOS = this.gson.fromJson(new FileReader(CITY_JSON), ImportCityDTO[].class);

        return Arrays.stream(importCityDTOS)
                .map(this::importCity)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importCity(ImportCityDTO importCityDTO) {
        Set<ConstraintViolation<ImportCityDTO>> violations = validator.validate(importCityDTO);

        if (!violations.isEmpty()) {
            return "Invalid city";
        }
        Optional<City> optionalCity = cityRepository.findByCityName(importCityDTO.getCityName());

        if (optionalCity.isPresent()) {
            return "Invalid city";
        }

        Optional<Country> optionalCountry = countryRepository.findById(importCityDTO.getCountry());

        if (optionalCountry.isEmpty()) {
            return "Invalid city";
        }

        City city = modelMapper.map(importCityDTO, City.class);
        city.setCountry(optionalCountry.get());
        cityRepository.save(city);

        return String.format("Successfully imported city %s - %s", city.getCityName(), city.getPopulation());
    }
}
