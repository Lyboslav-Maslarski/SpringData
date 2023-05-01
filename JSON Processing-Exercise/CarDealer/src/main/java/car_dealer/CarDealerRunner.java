package car_dealer;

import car_dealer.dtos.export_dtos.*;
import car_dealer.services.*;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CarDealerRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final Gson gson;
    private final CustomerService customerService;
    private final CarService carService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    @Autowired
    public CarDealerRunner(SeedService seedService, CustomerService customerService,
                           CarService carService, SupplierService supplierService,
                           SaleService saleService) {
        this.seedService = seedService;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;

        JsonSerializer<String> fromLocalDate =
                (date, t, c) -> new JsonPrimitive(date);
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, fromLocalDate)
                .create();
    }

    @Override
    public void run(String... args) throws Exception {
//        seedService.seedAll();
//        queryOne();
//        queryTwo();
//        queryThree();
//        queryFour();
//        queryFive();
        querySix();
    }

    private void querySix() {
        List<SaleDTO> saleDTOS = saleService.getAllSales();
        String json = gson.toJson(saleDTOS);
        System.out.println(json);
    }

    private void queryFive() {
        List<CustomerWithSalesDTO> customerWithSalesDTOS = customerService.getAllWithSales();
        String json = gson.toJson(customerWithSalesDTOS);
        System.out.println(json);
    }

    private void queryFour() {
        List<CarWithPartsDTO> carExportDTOS = carService.getAllWithParts();
        String json = gson.toJson(carExportDTOS);
        System.out.println(json);
    }

    private void queryThree() {
        List<SupplierNoImporterDTO> noImporterDTOS = supplierService.getNoImporters();
        String json = gson.toJson(noImporterDTOS);
        System.out.println(json);
    }

    private void queryTwo() {
        List<CarToyotaDTO> carToyotaDTOS = carService.getAllFromToyota("Toyota");
        String json = gson.toJson(carToyotaDTOS);
        System.out.println(json);
    }

    private void queryOne() {
        List<CustomerExportDTO> customerExportDTOS = customerService.getAllOrderByBirthDate();
        String json = gson.toJson(customerExportDTOS);
        System.out.println(json);
    }
}
