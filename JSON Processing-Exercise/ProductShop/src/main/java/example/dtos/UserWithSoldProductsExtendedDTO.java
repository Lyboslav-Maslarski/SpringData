package example.dtos;

public class UserWithSoldProductsExtendedDTO {
    private String firstName;
    private String lastName;
    private int age;
    private AllSoldProductsDTO soldProducts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AllSoldProductsDTO getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(AllSoldProductsDTO soldProducts) {
        this.soldProducts = soldProducts;
    }
}
