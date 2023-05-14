package example.services;

import example.dtos.UserWithSoldProductsDTO;
import example.dtos.UsersWithSoldProductsDTO;

import java.util.List;

public interface UserService {
    List<UserWithSoldProductsDTO> getAllWithSoldProducts();
    UsersWithSoldProductsDTO getAllWithSoldProductsOrderByCount();
}
