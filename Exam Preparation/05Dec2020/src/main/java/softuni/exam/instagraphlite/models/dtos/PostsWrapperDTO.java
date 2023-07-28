package softuni.exam.instagraphlite.models.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "posts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostsWrapperDTO {
    @XmlElement(name = "post")
    private List<PostImportDTO> posts;

    public PostsWrapperDTO() {
    }

    public List<PostImportDTO> getPosts() {
        return posts;
    }

    public PostsWrapperDTO setPosts(List<PostImportDTO> posts) {
        this.posts = posts;
        return this;
    }
}
