package exercise.dto.posts;

import java.util.List;
import exercise.model.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;


// BEGIN
public class PostsPage {
    private List<Post> posts;

    public PostsPage(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }
}

// END


