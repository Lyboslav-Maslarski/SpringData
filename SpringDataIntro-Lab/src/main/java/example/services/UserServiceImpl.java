package example.services;

import example.entities.User;
import example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        User found = userRepository.getByUsername(user.getUsername());
        if (found == null) {
            userRepository.save(user);
        }
    }
}
