package org.example.services;

import org.example.models.dtos.CompanyImportDTO;
import org.example.models.dtos.CompanyWrapperDTO;
import org.example.models.entities.Company;
import org.example.repositories.CompanyRepository;
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
public class CompanyService {
    public static final String COMPANIES_XML = "src/main/resources/files/xmls/companies.xml";
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public boolean isImported() {
        return this.companyRepository.count() > 0;
    }

    public String getCompaniesText() throws IOException {
        return Files.readString(Path.of(COMPANIES_XML));
    }

    public String importCompanies() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(CompanyWrapperDTO.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        CompanyWrapperDTO dto = (CompanyWrapperDTO) unmarshaller.unmarshal(new FileReader(COMPANIES_XML));

        return dto.getCompanies()
                .stream()
                .map(CompanyImportDTO::getName)
                .map(Company::new)
                .map(company -> {
                    if (this.companyRepository.findByName(company.getName()).isPresent()) {
                        return "Invalid Company";
                    }

                    this.companyRepository.save(company);

                    return "Created Company " + company.getName();
                })
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
