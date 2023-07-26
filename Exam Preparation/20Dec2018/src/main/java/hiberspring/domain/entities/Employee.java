package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String position;

    @OneToOne(optional = false)
    private EmployeeCard card;

    @ManyToOne(optional = false)
    private Branch branch;

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public Employee setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Employee setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Employee setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    public EmployeeCard getCard() {
        return card;
    }

    public Employee setCard(EmployeeCard card) {
        this.card = card;
        return this;
    }

    public Branch getBranch() {
        return branch;
    }

    public Employee setBranch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\n" +
                "Position: %s\n" +
                "Card Number: %s\n", getFullName(), getPosition(), getCard().getNumber());
    }
}
