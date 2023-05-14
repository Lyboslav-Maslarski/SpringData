package ProductShop.services;


import ProductShop.exportDTOS.UsersWithSoldItemAndCountDTO;
import ProductShop.exportDTOS.UsersWithSoldItemDTO;

public interface UserService {

    UsersWithSoldItemDTO getAllWithSoldItem();

    UsersWithSoldItemAndCountDTO getAllWithSoldItemAndCount();
}
