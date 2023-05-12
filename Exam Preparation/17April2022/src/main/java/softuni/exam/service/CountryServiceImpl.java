package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CountryServiceImpl implements CountryService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return null;
    }

    @Override
    public String importCountries() throws IOException {
        return null;
    }
}
