package softuni.exam.instagraphlite.models.entities;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToOne(optional = false)
    private Picture profilePicture;
    @OneToMany(targetEntity = Post.class, mappedBy = "user")
    private List<Post> posts;

    public User() {
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Picture getProfilePicture() {
        return profilePicture;
    }

    public User setProfilePicture(Picture profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User setPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("User: ").append(getUsername()).append(System.lineSeparator());
        sb.append("Post count: ").append(getPosts().size()).append(System.lineSeparator());

        getPosts().stream()
                .sorted(Comparator.comparingDouble(p -> p.getPicture().getSize()))
                .forEach(post -> sb.append("==Post Details:").append(System.lineSeparator())
                        .append("----Caption: ").append(post.getCaption()).append(System.lineSeparator())
                        .append(String.format("----Picture Size: %.2f", post.getPicture().getSize()))
                        .append(System.lineSeparator()));

        return sb.toString();
    }
}
