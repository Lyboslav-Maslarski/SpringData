package hiberspring.service;

import hiberspring.domain.dtos.ProductImportDTO;
import hiberspring.domain.dtos.ProductsWrapperDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.domain.entities.Town;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static hiberspring.common.Constants.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return fileUtil.readFile(PATH_TO_FILES + "products.xml");
    }

    @Override
    public String importProducts() throws JAXBException {
        ProductsWrapperDTO productsWrapperDTO = xmlParser.parseXml(ProductsWrapperDTO.class, PATH_TO_FILES + "products.xml");
        return productsWrapperDTO
                .getProducts()
                .stream()
                .map(this::importProduct)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importProduct(ProductImportDTO productImportDTO) {
        if (!validationUtil.isValid(productImportDTO)) {
            return INCORRECT_DATA_MESSAGE;
        }

        Product product = modelMapper.map(productImportDTO, Product.class);

        Optional<Branch> branch = branchRepository.findByName(productImportDTO.getBranch());
        if (branch.isEmpty()) {
            return INCORRECT_DATA_MESSAGE;
        }

        product.setBranch(branch.get());
        productRepository.save(product);

        return String.format(SUCCESSFUL_IMPORT_MESSAGE, product.getClass().getSimpleName(), product.getName());
    }
}
