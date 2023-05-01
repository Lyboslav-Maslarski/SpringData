package car_dealer.dtos.export_dtos;

import car_dealer.entities.Sale;

import java.util.ArrayList;
import java.util.List;

public class CustomerExportDTO {
    private int id;
    private String name;
    private String birthDate;
    private boolean isYoungDriver;
    private List<Sale> sales;

    public CustomerExportDTO() {
        this.sales = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
