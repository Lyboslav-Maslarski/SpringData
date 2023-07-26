package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.BranchImportDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.TownRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static hiberspring.common.Constants.*;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.branchRepository = branchRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return fileUtil.readFile(PATH_TO_FILES + "branches.json");
    }

    @Override
    public String importBranches(String branchesFileContent) {
        BranchImportDTO[] branchImportDTOS = gson.fromJson(branchesFileContent, BranchImportDTO[].class);

        return Arrays.stream(branchImportDTOS)
                .map(this::importBranch)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importBranch(BranchImportDTO branchImportDTO) {
        if (!validationUtil.isValid(branchImportDTO)) {
            return INCORRECT_DATA_MESSAGE;
        }

        Branch branch = modelMapper.map(branchImportDTO, Branch.class);

        Optional<Town> town = townRepository.findByName(branchImportDTO.getTown());
        if (town.isEmpty()) {
            return INCORRECT_DATA_MESSAGE;
        }

        branch.setTown(town.get());
        branchRepository.save(branch);

        return String.format(SUCCESSFUL_IMPORT_MESSAGE, branch.getClass().getSimpleName(), branch.getName());
    }
}
