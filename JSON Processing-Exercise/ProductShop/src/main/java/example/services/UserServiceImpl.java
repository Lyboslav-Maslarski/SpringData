package example.services;

import example.dtos.*;
import example.entities.User;
import example.repositories.UserRepository;
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
    public List<UserWithSoldProductsDTO> getAllWithSoldProducts() {
        List<User> allWithSoldProducts = userRepository.findAllWithSoldProducts();

        return allWithSoldProducts.stream()
                .map(user -> modelMapper.map(user, UserWithSoldProductsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsersWithSoldProductsDTO getAllWithSoldProductsOrderByCount() {
        List<User> allWithSoldProductsOrderByCount = userRepository.findAllWithSoldProductsOrderByCount();

        UsersWithSoldProductsDTO usersWithSoldProductsDTO = new UsersWithSoldProductsDTO();

        List<UserWithSoldProductsExtendedDTO> collect = allWithSoldProductsOrderByCount.stream()
                .map(user -> {
                    UserWithSoldProductsExtendedDTO userDTO = modelMapper.map(user, UserWithSoldProductsExtendedDTO.class);

                    AllSoldProductsDTO allSoldProductsDTO = new AllSoldProductsDTO();

                    List<SoldProductShortDTO> list = user.getSoldProducts().stream()
                            .filter(sale -> sale.getBuyer() != null)
                            .map(sale -> modelMapper.map(sale, SoldProductShortDTO.class))
                            .collect(Collectors.toList());

                    allSoldProductsDTO.setProducts(list);
                    allSoldProductsDTO.setCount(list.size());

                    userDTO.setSoldProducts(allSoldProductsDTO);
                    return userDTO;
                })
                .collect(Collectors.toList());

        usersWithSoldProductsDTO.setUsers(collect);
        usersWithSoldProductsDTO.setUsersCount(collect.size());
        return usersWithSoldProductsDTO;
    }
}
