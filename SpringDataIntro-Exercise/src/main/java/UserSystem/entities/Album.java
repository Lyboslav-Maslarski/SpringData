package UserSystem.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Color color;
    @Column(name = "is_public")
    private boolean isPublic;
    @ManyToOne
    private User user;
    @ManyToMany
    private Set<Picture> pictures;

    public Album() {
    }

    public Album(String name, Color color, String isPublic) {
        this.name = name;
        this.color = color;
        this.isPublic = isPublic.equalsIgnoreCase("yes");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
