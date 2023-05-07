package com.example.football.service.impl;

import com.example.football.models.dto.PlayerImportDTO;
import com.example.football.models.dto.PlayerWrapperDTO;
import com.example.football.models.dto.TeamImportDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    public static final String PLAYERS_XML = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository,
                             TeamRepository teamRepository, StatRepository statRepository, ModelMapper modelMapper, Validator validator) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYERS_XML));
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(PlayerWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PlayerWrapperDTO dto = (PlayerWrapperDTO) unmarshaller.unmarshal(new FileReader(PLAYERS_XML));
        return dto.getList().stream()
                .map(this::importPlayer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPlayer(PlayerImportDTO playerImportDTO) {
        Set<ConstraintViolation<PlayerImportDTO>> validate = validator.validate(playerImportDTO);
        if (!validate.isEmpty()) {
            return "Invalid Player";
        }

        if (this.playerRepository.findByEmail(playerImportDTO.getEmail()).isPresent()) {
            return "Invalid Player";
        }

        Player player = modelMapper.map(playerImportDTO, Player.class);

//        LocalDate date = LocalDate.parse(playerImportDTO.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Town town = townRepository.findByName(playerImportDTO.getTown().getName()).get();
        Team team = teamRepository.findByName(playerImportDTO.getTeam().getName()).get();
        Stat stat = statRepository.findById(playerImportDTO.getStat().getId()).get();
        player.setTown(town);
        player.setTeam(team);
        player.setStat(stat);
//        player.setBirthDate(date);

        playerRepository.save(player);

        return String.format("Successfully imported Player %s %s - %s",
                player.getFirstName(), player.getLastName(), player.getPosition().name());
    }

    @Override
    public String exportBestPlayers() {
        LocalDate after = LocalDate.of(1995, 1, 1);
        LocalDate before = LocalDate.of(2003, 1, 1);

        List<Player> players = this.playerRepository.findByBirthDateBetweenOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc(after, before);

        return players.stream()
                .map(Player::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
