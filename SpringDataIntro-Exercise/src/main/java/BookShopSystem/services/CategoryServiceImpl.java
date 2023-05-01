package BookShopSystem.services;

import BookShopSystem.entities.Category;
import BookShopSystem.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();

        long count = this.categoryRepository.count();

        int categoriesCount = random.nextInt((int) count) + 1;

        Set<Long> categoriesIDs = new HashSet<>();

        for (int i = 0; i < categoriesCount; i++) {
            long categoryId = random.nextInt((int) count) + 1L;
            categoriesIDs.add(categoryId);
        }

        List<Category> allById = this.categoryRepository.findAllById(categoriesIDs);

        return new HashSet<>(allById);
    }
}
