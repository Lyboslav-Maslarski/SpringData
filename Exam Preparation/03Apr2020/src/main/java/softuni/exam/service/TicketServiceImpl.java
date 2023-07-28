package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.TicketImportDTO;
import softuni.exam.models.dtos.TicketsWrapperDTO;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, TownRepository townRepository,
                             PassengerRepository passengerRepository, PlaneRepository planeRepository,
                             ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/xml/tickets.xml"));
    }

    @Override
    public String importTickets() throws JAXBException {
        TicketsWrapperDTO ticketsWrapperDTO = xmlParser
                .parseXml(TicketsWrapperDTO.class, "src/main/resources/files/xml/tickets.xml");

        return ticketsWrapperDTO.getTickets().stream()
                .map(this::importTicket)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTicket(TicketImportDTO ticketImportDTO) {
        if (!validationUtil.isValid(ticketImportDTO)) {
            return "Invalid Ticket";
        }

        Optional<Ticket> existingTicket = ticketRepository.findBySerialNumber(ticketImportDTO.getSerialNumber());
        if (existingTicket.isPresent()) {
            return "Invalid Ticket";
        }

        Optional<Town> fromTown = townRepository.findByName(ticketImportDTO.getFromTown().getName());
        if (fromTown.isEmpty()){
            return "Invalid Ticket";
        }

        Optional<Town> toTown = townRepository.findByName(ticketImportDTO.getToTown().getName());
        if (toTown.isEmpty()){
            return "Invalid Ticket";
        }

        Optional<Passenger> passenger = passengerRepository.findByEmail(ticketImportDTO.getPassenger().getEmail());
        if (passenger.isEmpty()){
            return "Invalid Ticket";
        }

        Optional<Plane> plane = planeRepository.findByRegisterNumber(ticketImportDTO.getPlane().getRegisterNumber());
        if (plane.isEmpty()){
            return "Invalid Ticket";
        }

        Ticket ticket = modelMapper.map(ticketImportDTO, Ticket.class);
        ticket.setFromTown(fromTown.get());
        ticket.setToTown(toTown.get());
        ticket.setPassenger(passenger.get());
        ticket.setPlane(plane.get());
        ticket.setTakeoff(LocalDateTime.parse(ticketImportDTO.getTakeoff(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        ticketRepository.save(ticket);

        return String.format("Successfully imported Ticket %s - %s",
                ticket.getFromTown().getName(), ticket.getToTown().getName());
    }
}
