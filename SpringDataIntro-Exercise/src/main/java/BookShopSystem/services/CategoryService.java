package BookShopSystem.services;

import BookShopSystem.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
