package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.PictureImportDTO;
import softuni.exam.domain.dtos.PicturesWrapperDTO;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil,
                              ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public String importPictures() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(PicturesWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PicturesWrapperDTO picturesWrapperDTO =
                (PicturesWrapperDTO) unmarshaller.unmarshal(new File("src/main/resources/files/xml/pictures.xml"));

        return picturesWrapperDTO
                .getPictures()
                .stream()
                .map(this::importPicture)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importPicture(PictureImportDTO pictureImportDTO) {
        if (!validatorUtil.isValid(pictureImportDTO)) {
            return "Invalid picture";
        }

        Picture picture = modelMapper.map(pictureImportDTO, Picture.class);
        pictureRepository.save(picture);

        return "Successfully imported picture - " + picture.getUrl();
    }

    @Override
    public boolean areImported() {
        return pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException {
        return fileUtil.readFile("src/main/resources/files/xml/pictures.xml");
    }
}
