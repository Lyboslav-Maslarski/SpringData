package CarDealer;

import CarDealer.dtos.*;
import CarDealer.dtos.export_dtos.*;
import CarDealer.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.List;

@Component
public class CarDealerRunner implements CommandLineRunner {
    private final SeedService seedService;
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
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * CHECK DB name in application.properties first
         */
//     seedService.seedAll();
//        queryOne();
//        queryTwo();
//        queryThree();
//        queryFour();
//        queryFive();
        querySix();
    }

    private void queryOne() throws JAXBException {
        CustomersExportDTO dto = customerService.getAllOrderByBirthDate();

        JAXBContext context = JAXBContext.newInstance(CustomersExportDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }

    private void queryTwo() throws JAXBException {
        CarsToyotaDTO dto = carService.getAllFromToyota("Toyota");

        JAXBContext context = JAXBContext.newInstance(CarsToyotaDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }

    private void queryThree() throws JAXBException {
        SuppliersNoImporterDTO dto = supplierService.getNoImporters();

        JAXBContext context = JAXBContext.newInstance(SuppliersNoImporterDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }

    private void queryFour() throws JAXBException {
        CarsWithPartsDTO dto = carService.getAllWithParts();

        JAXBContext context = JAXBContext.newInstance(CarsWithPartsDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }

    private void queryFive() throws JAXBException {
        CustomersWithSalesDTO dto = customerService.getAllWithSales();

        JAXBContext context = JAXBContext.newInstance(CustomersWithSalesDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }

    private void querySix() throws JAXBException {
        SalesDTO dto = saleService.getAllSales();

        JAXBContext context = JAXBContext.newInstance(SalesDTO.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(dto, System.out);
    }
}
