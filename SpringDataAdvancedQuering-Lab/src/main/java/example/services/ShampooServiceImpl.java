package example.services;

import example.entities.Shampoo;
import example.entities.Size;
import example.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {
    @Autowired
    private ShampooRepository shampooRepository;

    @Override
    public List<Shampoo> selectShampoosBySize(Size size) {
        return this.shampooRepository.findBySizeOrderByIdAsc(size);
    }

    @Override
    public List<Shampoo> selectShampoosBySizeOrLabel(Size size, int labelId) {
        return this.shampooRepository.findBySizeOrLabelIdOrderByPrice(size, labelId);
    }

    @Override
    public List<Shampoo> selectShampoosByPrice(BigDecimal price) {
        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countShampoosByPrice(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredients(Set<String> ingredientsNames) {
        return this.shampooRepository.findByIngredientsIn(ingredientsNames);
    }

    @Override
    public List<Shampoo> selectShampoosByIngredientsCount(int ingredientCount) {
        return this.shampooRepository.findByIngredientsCount(ingredientCount);
    }
}
