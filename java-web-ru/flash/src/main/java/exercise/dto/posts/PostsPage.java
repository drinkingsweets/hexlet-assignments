package exercise.dto.posts;

import java.util.List;

import exercise.dto.BasePage;
import exercise.model.Post;
import exercise.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

// BEGIN
public class PostsPage extends BasePage {
    private static List<Post> all = PostRepository.getEntities();

    public static List<Post> getAll() {
        return all;
    }
}
// END
