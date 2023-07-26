package softuni.exam.domain.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private BigDecimal salary;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Position position;
    @ManyToOne(optional = false)
    private Picture picture;
    @ManyToOne(optional = false)
    private Team team;

    public Player() {
    }

    public Integer getId() {
        return id;
    }

    public Player setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Player setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Player setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public Player setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public Player setSalary(BigDecimal salary) {
        this.salary = salary;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public Player setPosition(Position position) {
        this.position = position;
        return this;
    }

    public Picture getPicture() {
        return picture;
    }

    public Player setPicture(Picture picture) {
        this.picture = picture;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Player setTeam(Team team) {
        this.team = team;
        return this;
    }
}
