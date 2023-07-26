package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PlayerImportDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, PictureRepository pictureRepository,
                             TeamRepository teamRepository, FileUtil fileUtil, ValidatorUtil validatorUtil,
                             ModelMapper modelMapper, Gson gson) {
        this.playerRepository = playerRepository;
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public String importPlayers() throws IOException {
        PlayerImportDTO[] playerImportDTOS = gson.fromJson(readPlayersJsonFile(), PlayerImportDTO[].class);

        return Arrays.stream(playerImportDTOS)
                .map(this::importPlayer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPlayer(PlayerImportDTO playerImportDTO) {
        if (!validatorUtil.isValid(playerImportDTO)) {
            return "Invalid player";
        }

        Optional<Picture> picture = pictureRepository.findByUrl(playerImportDTO.getPicture().getUrl());
        if (picture.isEmpty()) {
            return "Invalid player";
        }

        Optional<Team> team = teamRepository.findByName(playerImportDTO.getTeam().getName());
        if (team.isEmpty()) {
            return "Invalid player";
        }

        boolean existingPosition = Arrays.stream(Position.values())
                .anyMatch(p -> p.name().equals(playerImportDTO.getPosition()));
        if (!existingPosition) {
            return "Invalid player";
        }
        Position position = Position.valueOf(playerImportDTO.getPosition());

        Player player = modelMapper.map(playerImportDTO, Player.class);
        player.setPicture(picture.get());
        player.setTeam(team.get());
        player.setPosition(position);
        playerRepository.save(player);

        return "Successfully imported player: " + player.getFullName();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile("src/main/resources/files/json/players.json");
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        return playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(BigDecimal.valueOf(100000))
                .stream()
                .map(Player::playerBySalary)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder stringBuilder = new StringBuilder("Team: North Hub");

        playerRepository.findAllByTeamNameOrderById("North Hub")
                .forEach(p -> stringBuilder.append(System.lineSeparator()).append(p.playerByTeam()));

        return stringBuilder.toString();
    }
}
