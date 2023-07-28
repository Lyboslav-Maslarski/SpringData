package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class AgentServiceImpl implements AgentService {
    @Override
    public boolean areImported() {
        return false;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return null;
    }

    @Override
    public String importAgents() throws IOException {
        return null;
    }
}
