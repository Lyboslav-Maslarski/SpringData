package softuni.exam.instagraphlite.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.UserImportDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository,
                           ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/users.json"));
    }

    @Override
    public String importUsers() throws IOException {
        UserImportDTO[] userImportDTOS = gson.fromJson(readFromFileContent(), UserImportDTO[].class);

        return Arrays.stream(userImportDTOS)
                .map(this::importUser)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importUser(UserImportDTO userImportDTO) {
        if (!validationUtil.isValid(userImportDTO)) {
            return "Invalid User";
        }

        Optional<User> existingUser = userRepository.findByUsername(userImportDTO.getUsername());
        if (existingUser.isPresent()) {
            return "Invalid User";
        }

        Optional<Picture> picture = pictureRepository.findByPath(userImportDTO.getProfilePicture());
        if (picture.isEmpty()) {
            return "Invalid User";
        }

        User user = modelMapper.map(userImportDTO, User.class);
        user.setProfilePicture(picture.get());
        userRepository.save(user);

        return String.format("Successfully imported User: %s", user.getUsername());
    }

    @Override
    @Transactional
    public String exportUsersWithTheirPosts() {
        return userRepository.findAllByOrderByPostsDescIdAsc()
                .stream()
                .map(User::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
