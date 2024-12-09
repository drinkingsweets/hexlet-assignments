package exercise.dto;

import java.util.List;
import java.util.stream.Collectors;

import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class PostDTO {
    private long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;

    public static PostDTO toPostDTO(Post post, CommentRepository commentRepository) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setBody(post.getBody());
        dto.setTitle(post.getTitle());
        dto.setComments(commentRepository.findByPostId(post.getId())
                .stream()
                .map(CommentDTO::toCommentDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
// END
