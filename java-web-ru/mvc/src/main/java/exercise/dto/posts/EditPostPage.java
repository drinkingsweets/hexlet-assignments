package exercise.dto.posts;

import java.util.List;
import java.util.Map;

import exercise.model.Post;
import io.javalin.validation.ValidationError;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class EditPostPage {
    private String name;
    private Post post;
    private String body;
    private Map<String, List<ValidationError<Object>>> errors;

    public EditPostPage(Post post, Map<String, List<ValidationError<Object>>> errors) {
        this.post = post;
        this.errors = errors;
    }

    public EditPostPage(Post post) {
        this.post = post;
    }
}
// END
