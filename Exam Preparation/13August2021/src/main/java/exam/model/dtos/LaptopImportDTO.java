package exam.model.dtos;

import exam.model.entities.WarrantyType;

import java.math.BigDecimal;

public class LaptopImportDTO {
    private String macAddress;
    private float cpuSpeed;
    private int ram;
    private int storage;
    private String description;
    private BigDecimal price;
    private WarrantyType warrantyType;
    private NameDTO shop;

    public String getMacAddress() {
        return macAddress;
    }

    public float getCpuSpeed() {
        return cpuSpeed;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public WarrantyType getWarrantyType() {
        return warrantyType;
    }

    public NameDTO getShop() {
        return shop;
    }

    public boolean isValid() {
        if (this.macAddress.length() < 9) {
            return false;
        }
        if (this.cpuSpeed < 0) {
            return false;
        }
        if (this.ram < 8 || this.ram > 128) {
            return false;
        }
        if (this.storage < 128 || this.storage > 1024) {
            return false;
        }
        if (this.description.length() < 10) {
            return false;
        }
        if (this.price.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }
        if (warrantyType == null) {
            return false;
        }
        return true;
    }
}
