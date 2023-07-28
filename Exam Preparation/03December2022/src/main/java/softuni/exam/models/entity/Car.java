package softuni.exam.models.entity;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "car_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CarType carType;
    @Column(name = "car_make", nullable = false)
    private String carMake;
    @Column(name = "car_model", nullable = false)
    private String carModel;
    @Column(nullable = false)
    private Integer year;
    @Column(name = "plate_number", nullable = false)
    private String plateNumber;
    @Column(nullable = false)
    private Integer kilometers;
    @Column(nullable = false)
    private Double engine;

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public Car setId(Long id) {
        this.id = id;
        return this;
    }

    public CarType getCarType() {
        return carType;
    }

    public Car setCarType(CarType carType) {
        this.carType = carType;
        return this;
    }

    public String getCarMake() {
        return carMake;
    }

    public Car setCarMake(String carMake) {
        this.carMake = carMake;
        return this;
    }

    public String getCarModel() {
        return carModel;
    }

    public Car setCarModel(String carModel) {
        this.carModel = carModel;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Car setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Car setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
        return this;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public Car setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    public Double getEngine() {
        return engine;
    }

    public Car setEngine(Double engine) {
        this.engine = engine;
        return this;
    }
}
