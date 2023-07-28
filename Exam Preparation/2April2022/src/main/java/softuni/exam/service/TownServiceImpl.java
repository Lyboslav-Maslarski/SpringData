package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TownServiceImpl implements TownService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return null;
    }

    @Override
    public String importTowns() throws IOException {
        return null;
    }
}
