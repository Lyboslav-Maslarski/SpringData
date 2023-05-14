package example.services;

import example.dtos.LoginDTO;
import example.dtos.RegisterDTO;
import example.entities.User;
import example.exceptions.ValidationException;
import example.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User currentUser;

    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.currentUser = null;
        this.mapper = new ModelMapper();
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (currentUser != null) {
            throw new ValidationException("User already logged in.");
        }

        User toRegister = mapper.map(registerDTO, User.class);

        long count = userRepository.count();

        if (count == 0) {
            toRegister.setAdmin(true);
        }

        return userRepository.save(toRegister);
    }

    @Override
    public Optional<User> login(LoginDTO loginDTO) {
        if (currentUser != null) {
            throw new ValidationException("User already logged in.");
        }
        Optional<User> user = userRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());

        user.ifPresent(value -> this.currentUser = value);

        return user;
    }

    @Override
    public User getCurrentLoggedUser() {
        return currentUser;
    }

    @Override
    public User logout() {
        User loggedUser = getCurrentLoggedUser();

        currentUser = null;

        return loggedUser;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }
}
