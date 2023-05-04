package exam.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
public class Laptop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "mac_address", nullable = false, unique = true)
    private String macAddress;
    @Column(name = "cpu_speed")
    private float cpuSpeed;
    private int ram;
    private int storage;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;
    @ManyToOne(optional = false)
    private Shop shop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public float getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantyType warrantyType) {
        this.warrantyType = warrantyType;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public String toString() {
        return "Laptop - " + macAddress + System.lineSeparator() +
                "*Cpu speed - " + cpuSpeed + System.lineSeparator() +
                "**Ram - " + ram + System.lineSeparator() +
                "***Storage - " + storage + System.lineSeparator() +
                "****Price - " + price + System.lineSeparator() +
                "#Shop name - " + shop.getName() + System.lineSeparator() +
                "##Town - " + shop.getTown().getName()+System.lineSeparator();
    }
}
