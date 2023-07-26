package hiberspring.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int clients;

    @ManyToOne(optional = false)
    private Branch branch;

    public Product() {
    }

    public long getId() {
        return id;
    }

    public Product setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public int getClients() {
        return clients;
    }

    public Product setClients(int clients) {
        this.clients = clients;
        return this;
    }

    public Branch getBranch() {
        return branch;
    }

    public Product setBranch(Branch branch) {
        this.branch = branch;
        return this;
    }
}
