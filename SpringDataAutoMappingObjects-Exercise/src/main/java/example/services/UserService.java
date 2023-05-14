package example.services;

import example.dtos.LoginDTO;
import example.dtos.RegisterDTO;
import example.entities.User;

import java.util.Optional;

public interface UserService {
    User register(RegisterDTO registerDTO);

    Optional<User> login(LoginDTO loginDTO);

    User getCurrentLoggedUser();

    User logout();

    void updateUser(User user);
}
