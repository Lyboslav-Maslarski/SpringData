package car_dealer.dtos.export_dtos;

import java.math.BigDecimal;

public class SaleDTO {
    private CarExportDTO car;
    private String customerName;
    private double discount;
    private BigDecimal price;
    private BigDecimal priceWithDiscount;

    public CarExportDTO getCar() {
        return car;
    }

    public void setCar(CarExportDTO car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
