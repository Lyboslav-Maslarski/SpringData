package example.services;

import example.dtos.GameDTO;
import example.dtos.GameViewDTO;
import example.dtos.LoginDTO;
import example.dtos.RegisterDTO;
import example.entities.Game;
import example.entities.Order;
import example.entities.User;
import example.exceptions.IllegalAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExecutorServiceImpl implements ExecutorService {
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public ExecutorServiceImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public String execute(String commandLine) {
        String[] commandParts = commandLine.split("\\|");

        String commandName = commandParts[0];

        return switch (commandName) {
            case REGISTER_USER_COMMAND -> registerUser(commandParts);
            case LOGIN_USER_COMMAND -> logInUser(commandParts);
            case LOGOUT_USER_COMMAND -> logOutUser();
            case ADD_GAME_COMMAND -> addGame(commandParts);
            case EDIT_GAME_COMMAND -> editGame(commandParts);
            case DELETE_GAME_COMMAND -> deleteGame(commandParts);
            case VIEW_ALL_GAMES_COMMAND -> viewAllGames();
            case VIEW_DETAILS_FOR_GAME_COMMAND -> viewDetailsForAGame(commandParts[1]);
            case VIEW_OWNED_GAMES_COMMAND -> viewOwnedGames();
            case ADD_GAME_TO_CART_COMMAND -> addGameToCart(commandParts[1]);
            case REMOVE_GAME_FROM_CART_COMMAND -> removeGameFromCart(commandParts[1]);
            case BUY_ALL_GAMES_COMMAND -> buyGames();
            default -> throw new IllegalStateException("Unexpected value: " + commandName);
        };
    }

    private String buyGames() {
        User user = getUser();

        Optional<Order> optionalOrder = orderService.getOrder(user.getId());

        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty.");
        }
        Order order = optionalOrder.get();
        Set<Game> products = order.getProducts();
        user.getGames().addAll(products);

        orderService.deleteOrder(order);
        userService.updateUser(user);

        StringBuilder sb = new StringBuilder("Successfully bought games:");

        products.forEach(g -> sb.append(System.lineSeparator()).append(" -").append(g.getTitle()));

        return sb.toString();
    }

    private String removeGameFromCart(String gameName) {
        User user = getUser();

        Optional<Order> optionalOrder = orderService.getOrder(user.getId());

        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Shopping cart is empty.");
        } else {
            Set<Game> games = optionalOrder.get().getProducts();

            boolean removed = games.removeIf(game -> game.getTitle().equals(gameName));

            if (!removed) {
                throw new IllegalArgumentException("No such game in shopping cart.");
            }
        }

        orderService.saveOrder(optionalOrder.get());
        return gameName + " removed from cart.";
    }

    private String addGameToCart(String gameName) {
        User user = getUser();

        Optional<Game> gameToBeBought = gameService.getGame(gameName);
        if (gameToBeBought.isEmpty()) {
            throw new IllegalArgumentException("No such game in database.");
        }

        Set<Game> ownedGames = user.getGames();

        if (ownedGames.stream().anyMatch(game -> game.getTitle().equals(gameName))) {
            throw new IllegalArgumentException("User already owns the game.");
        }

        Optional<Order> optionalOrder = orderService.getOrder(user.getId());
        Order order;
        if (optionalOrder.isEmpty()) {

            order = new Order(user);
            order.getProducts().add(gameToBeBought.get());
        } else {
            order = optionalOrder.get();
            Set<Game> gamesToBeBought = order.getProducts();

            if (gamesToBeBought.stream().anyMatch(game -> game.getTitle().equals(gameName))) {
                throw new IllegalArgumentException("User has added the game to the cart already.");
            }

            gamesToBeBought.add(gameToBeBought.get());
        }

        orderService.saveOrder(order);
        return gameName + " added to cart.";
    }

    private String viewOwnedGames() {
        User user = getUser();

        Set<Game> games = user.getGames();

        if (games.isEmpty()) {
            return "List of owned games is empty.";
        }

        return games.stream().map(Game::getTitle).collect(Collectors.joining(System.lineSeparator()));
    }

    private String viewDetailsForAGame(String gameName) {
        Optional<Game> game = gameService.getGame(gameName);

        if (game.isEmpty()) {
            return "No such game in the database.";
        }

        return game.get().toString();
    }

    private String viewAllGames() {
        Set<GameViewDTO> games = gameService.getAllGames();

        StringBuilder sb = new StringBuilder();

        games.forEach(g -> sb.append(g).append(System.lineSeparator()));

        return sb.toString().trim();
    }

    private String deleteGame(String[] commandParts) {
        checkUserStatus();

        Game game = gameService.deleteGame(Integer.parseInt(commandParts[1]));

        return "Deleted " + game.getTitle();
    }

    private String editGame(String[] commandParts) {
        checkUserStatus();

        Game game = gameService.editGame(commandParts);

        return "Edited " + game.getTitle();
    }

    private String addGame(String[] commandParts) {
        checkUserStatus();

        GameDTO gameDTO = new GameDTO(commandParts);

        Game game = gameService.addGame(gameDTO);

        return "Added " + game.getTitle();
    }

    private String logOutUser() {
        User user = userService.logout();

        if (user == null) {
            return "Cannot log out. No user was logged in.";
        }

        return String.format("User %s successfully logged out", user.getFullName());
    }

    private String logInUser(String[] commandParts) {
        LoginDTO loginDTO = new LoginDTO(commandParts);

        Optional<User> user = userService.login(loginDTO);

        if (user.isPresent()) {
            return String.format("Successfully logged in %s", user.get().getFullName());
        }

        return "Incorrect username / password";
    }

    private String registerUser(String[] commandParts) {
        RegisterDTO registerDTO = new RegisterDTO(commandParts);

        User user = userService.register(registerDTO);

        return String.format("%s was registered", user.getFullName());
    }

    private void checkUserStatus() {
        User loggedUser = getUser();

        if (!loggedUser.isAdmin()) {
            throw new IllegalAccessException("User cannot modify catalog.");
        }
    }

    private User getUser() {
        User user = userService.getCurrentLoggedUser();

        if (user == null) {
            throw new IllegalAccessException("No user currently logged in.");
        }
        return user;
    }

}
