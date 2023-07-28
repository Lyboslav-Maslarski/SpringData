package softuni.exam.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
@Service
public class ApartmentServiceImpl implements ApartmentService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        return null;
    }
}
