package softuni.exam.service;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class OfferServiceImpl implements OfferService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return null;
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        return null;
    }

    @Override
    public String exportOffers() {
        return null;
    }
}
