package example.dtos;

import java.util.List;

public class AllSoldProductsDTO {
    private Integer count;
    private List<SoldProductShortDTO> products;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SoldProductShortDTO> getProducts() {
        return products;
    }

    public void setProducts(List<SoldProductShortDTO> products) {
        this.products = products;
    }
}
