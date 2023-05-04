package example.services;

import example.dtos.GameDTO;
import example.dtos.GameViewDTO;
import example.entities.Game;

import java.util.Optional;
import java.util.Set;

public interface GameService {
    Game addGame(GameDTO gameDTO);

    Game editGame(String[] commandParts);

    Game deleteGame(int id);

    Set<GameViewDTO> getAllGames();

    Optional<Game> getGame(String gameName);
}
