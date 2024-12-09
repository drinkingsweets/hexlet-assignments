package exercise.controller;

import exercise.repository.CommentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping(path = "/posts")
public class PostsController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    List<PostDTO> show() {
        var posts = postRepository.findAll();
        var result = posts
                .stream()
                .map((Post post) -> PostDTO.toPostDTO(post, commentRepository))
                .toList();
        return result;
    }

    @GetMapping(path = "/{id}")
    PostDTO showPost(@PathVariable long id) {
        PostDTO postDTO = PostDTO.toPostDTO(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found")),
                commentRepository);
        return postDTO;
    }
}
// END
