package exercise.controller;

import exercise.model.Post;
import exercise.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping(path = "/comments")
public class CommentsController {
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    List<Comment> show() {
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    Optional<Comment> exact(@PathVariable long id) {
        return commentRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Comment create(@RequestBody Comment Comment) {
        return commentRepository.save(Comment);
    }

    @PutMapping(path = "/{id}")
    Comment update(@PathVariable long id, @RequestBody Comment Comment) {
        var found = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment " + id + " not found."));
        found.setBody(Comment.getBody());
        found.setCreatedAt(Comment.getCreatedAt());
        return commentRepository.save(found);
    }

    @DeleteMapping(path = "/{id}")
    void delete(@PathVariable long id) {
        commentRepository.deleteById(id);
    }

}
// END
