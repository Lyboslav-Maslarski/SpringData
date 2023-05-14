package org.example.services;

import org.example.models.dtos.RegistrationDTO;
import org.example.models.entities.User;
import org.example.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(@Qualifier("default") ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public void register(RegistrationDTO dto) {
        User user = this.modelMapper.map(dto, User.class);

        this.userRepository.save(user);
    }
}
