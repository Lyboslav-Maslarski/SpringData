package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCityDTO;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastsDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final CityRepository cityRepository;
    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final String FORECAST_XML = "src/main/resources/files/xml/forecasts.xml";

    public ForecastServiceImpl(CityRepository cityRepository, ForecastRepository forecastRepository, ModelMapper modelMapper, Validator validator) {
        this.cityRepository = cityRepository;
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECAST_XML));
    }

    @Override
    public String importForecasts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(ImportForecastsDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ImportForecastsDTO importForecastsDTO = (ImportForecastsDTO) unmarshaller.unmarshal(new FileReader(FORECAST_XML));
        return importForecastsDTO
                .getForecasts().stream()
                .map(this::importForecast)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importForecast(ImportForecastDTO importForecastDTO) {
        Set<ConstraintViolation<ImportForecastDTO>> violations = validator.validate(importForecastDTO);

        if (!violations.isEmpty()) {
            return "Invalid forecast";
        }
        Optional<Forecast> optionalForecast = forecastRepository.findByCityId(importForecastDTO.getCity());

        if (optionalForecast.isPresent()) {
            return "Invalid forecast";
        }

        Optional<City> optionalCity = cityRepository.findById(importForecastDTO.getCity());

        if (optionalCity.isEmpty()) {
            return "Invalid forecast";
        }

        Forecast forecast = modelMapper.map(importForecastDTO, Forecast.class);
        forecast.setCity(optionalCity.get());
        forecastRepository.save(forecast);

        return String.format("Successfully imported forecast %s - %s", forecast.getDayOfWeek().name(), forecast.getMaxTemperature());
    }

    @Override
    public String exportForecasts() {
        return null;
    }
}
