package com.example.football.service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public interface PlayerService {
    boolean areImported();

    String readPlayersFileContent() throws IOException;

    String importPlayers() throws JAXBException, FileNotFoundException;

    String exportBestPlayers();
}
