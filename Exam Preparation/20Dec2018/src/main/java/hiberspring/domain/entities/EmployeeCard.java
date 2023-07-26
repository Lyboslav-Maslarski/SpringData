package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "employee_cards")
public class EmployeeCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String number;

    public EmployeeCard() {
    }

    public long getId() {
        return id;
    }

    public EmployeeCard setId(long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public EmployeeCard setNumber(String number) {
        this.number = number;
        return this;
    }
}
