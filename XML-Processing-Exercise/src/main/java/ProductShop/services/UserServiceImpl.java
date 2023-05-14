package ProductShop.services;


import ProductShop.entities.User;
import ProductShop.exportDTOS.*;
import ProductShop.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    @Transactional
    public UsersWithSoldItemDTO getAllWithSoldItem() {
        List<User> users = this.userRepository.findAllWithSoldProducts();

        List<UserWithSoldItemDTO> dtos = users.stream()
                .map(user -> modelMapper.map(user, UserWithSoldItemDTO.class))
                .collect(Collectors.toList());

        return new UsersWithSoldItemDTO(dtos);
    }

    @Override
    @Transactional
    public UsersWithSoldItemAndCountDTO getAllWithSoldItemAndCount() {
        List<User> users = this.userRepository.findAllWithAtLeastOneProduct();

        List<UserWithSoldItemAndCountDTO> dtos = users.stream()
                .map(user -> {
                    UserWithSoldItemAndCountDTO dto = modelMapper.map(user, UserWithSoldItemAndCountDTO.class);

                    List<ProductExportDTO> products = user.getSoldProducts().stream()
                            .map(product -> modelMapper.map(product, ProductExportDTO.class))
                            .collect(Collectors.toList());

                    SoldProductsDTO soldProductsDTO = new SoldProductsDTO(products);
                    dto.setSoldProducts(soldProductsDTO);

                    return dto;
                })
                .collect(Collectors.toList());

        return new UsersWithSoldItemAndCountDTO(dtos);
    }
}
