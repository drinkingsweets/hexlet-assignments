package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import exercise.dto.posts.PostsPage;
import exercise.dto.posts.PostPage;
import exercise.model.Post;
import exercise.repository.PostRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostsController {

    // BEGIN
    public static void showPosts(Context ctx) {
        int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        List<Post> toShow = PostRepository.findAll(page, 5);
        ctx.render("posts/index.jte", Map.of("page", new PostsPage(toShow),
                "currentPage", page));
    }

    public static void showCurrent(Context ctx) {
        long id = ctx.pathParamAsClass("id", Long.class).get();
        Optional<Post> post = PostRepository.find(id);
        if (post.isPresent()) {
            ctx.render("posts/show.jte", model("post", post.get()));
        }
        else {
            ctx.status(404).result("Page not found");
        }
    }
    // END
}
