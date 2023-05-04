package example.services;

import example.dtos.GameDTO;
import example.dtos.GameViewDTO;
import example.entities.Game;
import example.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public Game addGame(GameDTO gameDTO) {
        Optional<Game> existingGame = gameRepository.findByTitle(gameDTO.getTitle());

        if (existingGame.isPresent()) {
            throw new IllegalArgumentException("Game with the same name already exist.");
        }
        TypeMap<GameDTO, Game> typeMap = mapper.createTypeMap(GameDTO.class, Game.class);
        typeMap.addMappings(mapping -> mapping.skip(Game::setId));
        Game game = typeMap.map(gameDTO);

        return gameRepository.save(game);
    }

    @Override
    public Game editGame(String[] commandParts) {
        Optional<Game> game = gameRepository.findById(Integer.parseInt(commandParts[1]));
        if (game.isEmpty()) {
            throw new IllegalArgumentException("No game with current id exists.");
        }
        Game currentGame = game.get();
        for (int i = 2; i < commandParts.length; i++) {
            String commandPart = commandParts[i];
            String field = commandPart.split("=")[0];
            String value = commandPart.split("=")[1];
            switch (field) {
                case "title" -> currentGame.setTitle(value);
                case "price" -> currentGame.setPrice(new BigDecimal(value));
                case "size" -> currentGame.setSize(Float.parseFloat(value));
                case "thumbnail" -> currentGame.setThumbnailUrl(value);
                case "trailer" -> currentGame.setTrailerId(value);
                case "description" -> currentGame.setDescription(value);
                case "date" ->
                        currentGame.setReleaseDate(LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                default -> throw new IllegalArgumentException("No such parameter for entity type - Game.");
            }
        }

        return gameRepository.save(currentGame);
    }

    @Override
    public Game deleteGame(int id) {
        Optional<Game> game = gameRepository.findById(id);

        if (game.isEmpty()) {
            throw new IllegalArgumentException("No game with current id exists.");
        }

        gameRepository.deleteById(id);

        return game.get();
    }

    @Override
    public Set<GameViewDTO> getAllGames() {
        return gameRepository.findAll().stream()
                .map(g -> mapper.map(g, GameViewDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Game> getGame(String gameName) {
        return gameRepository.findByTitle(gameName);
    }

}
