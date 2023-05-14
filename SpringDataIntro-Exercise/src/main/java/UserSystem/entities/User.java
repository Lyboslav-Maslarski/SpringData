package UserSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Transient
    private String fullName;
    @Column(nullable = false)
    @Size(min = 4, max = 30, message = "Username should be between 4 and 30 characters.")
    private String username;
    @NotNull
    @Password(minLength = 6,
            maxLength = 50,
            containsDigit = true,
            containsLowerCase = true,
            containsUpperCase = true,
            containsSpecialSymbols = true,
    message = "Invalid password.")
    private String password;
    @NotNull
    @Email(regexp = "\\b(?<user>[A-Za-z][A-Za-z0-9-._]+[A-Za-z])@(?<host>[a-z][a-z-.]+[a-z]\\.[a-z]{2,})\\b",message = "Invalid email.")
    private String email;
    @Column(name = "registered_on")
    private LocalDate registeredOn;
    @Column(name = "last_time_logged_in")
    private LocalDate lastTimeLoggedIn;
    @Min(value = 1, message = "Age should be in range [1, 120].")
    @Max(value = 120, message = "Age should be in range [1, 120].")
    private short age;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @ManyToOne
    private Town bornTown;
    @ManyToOne
    private Town currentTown;
    @ManyToMany
    private Set<User> friends;
    @OneToMany(mappedBy = "user", targetEntity = Album.class)
    private Set<Album> albums;

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public User() {
    }

    public User(String firstName, String lastName, String username,
                String password, String email,
                LocalDate registeredOn, LocalDate lastTimeLoggedIn,
                short age, Town bornTown, Town currentTown) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registeredOn = registeredOn;
        this.lastTimeLoggedIn = lastTimeLoggedIn;
        this.age = age;
        this.bornTown = bornTown;
        this.currentTown = currentTown;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFullName() {
        fullName = firstName + " " + lastName;
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public LocalDate getLastTimeLoggedIn() {
        return lastTimeLoggedIn;
    }

    public void setLastTimeLoggedIn(LocalDate lastTimeLoggedIn) {
        this.lastTimeLoggedIn = lastTimeLoggedIn;
    }

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Town getBornTown() {
        return bornTown;
    }

    public void setBornTown(Town bornTown) {
        this.bornTown = bornTown;
    }

    public Town getCurrentTown() {
        return currentTown;
    }

    public void setCurrentTown(Town currentTown) {
        this.currentTown = currentTown;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }
}
