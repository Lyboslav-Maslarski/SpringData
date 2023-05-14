package exam.service;

import exam.model.dtos.TownImportDTO;
import exam.model.dtos.TownWrapperDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.interfaces.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    public static final String TOWNS_XML = "src/main/resources/files/xml/towns.xml";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readAllLines(Path.of(TOWNS_XML)).stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        FileReader fileReader = new FileReader(TOWNS_XML);

        JAXBContext context = JAXBContext.newInstance(TownWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TownWrapperDTO dto = (TownWrapperDTO) unmarshaller.unmarshal(fileReader);

        return dto.getList().stream()
                .map(this::importTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTown(TownImportDTO townImportDTO) {
        if (!townImportDTO.isValid()) {
            return "Invalid town";
        }
        if (townRepository.findByName(townImportDTO.getName()).isPresent()) {
            return "Invalid town";
        }

        Town town = modelMapper.map(townImportDTO, Town.class);
        this.townRepository.save(town);

        return "Successfully imported Town " + town.getName();
    }
}
