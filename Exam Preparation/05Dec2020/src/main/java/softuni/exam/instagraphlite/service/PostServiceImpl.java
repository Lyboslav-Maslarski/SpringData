package softuni.exam.instagraphlite.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dtos.PostImportDTO;
import softuni.exam.instagraphlite.models.dtos.PostsWrapperDTO;
import softuni.exam.instagraphlite.models.entities.Picture;
import softuni.exam.instagraphlite.models.entities.Post;
import softuni.exam.instagraphlite.models.entities.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           PictureRepository pictureRepository, ModelMapper modelMapper,
                           XmlParser xmlParser, ValidationUtil validationUtil) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/posts.xml"));
    }

    @Override
    public String importPosts() throws JAXBException {
        PostsWrapperDTO postsWrapperDTO = xmlParser
                .parseXml(PostsWrapperDTO.class, "src/main/resources/files/posts.xml");

        return postsWrapperDTO.getPosts()
                .stream()
                .map(this::importPost)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPost(PostImportDTO postImportDTO) {
        if (!validationUtil.isValid(postImportDTO)) {
            return "Invalid Post";
        }

        Optional<User> user = userRepository.findByUsername(postImportDTO.getUser().getUsername());
        if (user.isEmpty()) {
            return "Invalid Post";
        }

        Optional<Picture> picture = pictureRepository.findByPath(postImportDTO.getPicture().getPath());
        if (picture.isEmpty()) {
            return "Invalid Post";
        }

        Post post = modelMapper.map(postImportDTO, Post.class);
        post.setPicture(picture.get());
        post.setUser(user.get());
        postRepository.save(post);

        return String.format("Successfully imported Post, made by %s", user.get().getUsername());
    }
}
