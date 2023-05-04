package example.services;

public interface ExecutorService {
    String REGISTER_USER_COMMAND = "RegisterUser";
    String LOGIN_USER_COMMAND = "LoginUser";
    String LOGOUT_USER_COMMAND = "Logout";
    String ADD_GAME_COMMAND = "AddGame";
    String EDIT_GAME_COMMAND = "EditGame";
    String DELETE_GAME_COMMAND = "DeleteGame";
    String VIEW_ALL_GAMES_COMMAND = "AllGames";
    String VIEW_DETAILS_FOR_GAME_COMMAND = "DetailGame";
    String VIEW_OWNED_GAMES_COMMAND = "OwnedGames";
    String ADD_GAME_TO_CART_COMMAND = "AddItem";
    String REMOVE_GAME_FROM_CART_COMMAND = "RemoveItem";
    String BUY_ALL_GAMES_COMMAND = "BuyItem";

    String execute(String command);
}
