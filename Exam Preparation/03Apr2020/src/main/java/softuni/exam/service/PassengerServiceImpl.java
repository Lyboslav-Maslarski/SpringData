package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PassengerImportDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository,
                                ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/passengers.json"));
    }

    @Override
    public String importPassengers() throws IOException {
        PassengerImportDTO[] passengerImportDTOS = gson.fromJson(readPassengersFileContent(), PassengerImportDTO[].class);
        return Arrays.stream(passengerImportDTOS)
                .map(this::importPassenger)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPassenger(PassengerImportDTO passengerImportDTO) {
        if (!validationUtil.isValid(passengerImportDTO)) {
            return "Invalid Passenger";
        }

        Optional<Passenger> existingPassenger = passengerRepository.findByEmail(passengerImportDTO.getEmail());
        if (existingPassenger.isPresent()) {
            return "Invalid Passenger";
        }

        Optional<Town> town = townRepository.findByName(passengerImportDTO.getTown());
        if (town.isEmpty()){
            return "Invalid Passenger";
        }

        Passenger passenger = modelMapper.map(passengerImportDTO, Passenger.class);
        passenger.setTown(town.get());
        passengerRepository.save(passenger);

        return String.format("Successfully imported Passenger %s - %s", passenger.getLastName(), passenger.getEmail());
    }

    @Override
    @Transactional
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return this.passengerRepository
                .findAllByOrderByTicketsDescEmailAsc()
                .stream()
                .map(Passenger::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
