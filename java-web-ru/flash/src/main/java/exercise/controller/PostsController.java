package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import exercise.dto.posts.PostsPage;
import exercise.dto.posts.PostPage;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.dto.posts.BuildPostPage;
import io.javalin.http.Context;
import io.javalin.validation.ValidationError;
import io.javalin.validation.ValidationException;
import io.javalin.http.NotFoundResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostsController {

    public static void build(Context ctx) {
        var page = new BuildPostPage();
        ctx.render("posts/build.jte", model("page", page));
    }

    // BEGIN
    public static void index(Context ctx) {
        ctx.render("posts/index.jte", Map.of("posts", PostsPage.getAll(),
                "flash", PostsPage.getFlash()));
        PostsPage.setFlash("");
    }

    public static void showForm(Context ctx) {
        ctx.render("posts/build.jte", model("page", new BuildPostPage("", "",
                new HashMap<String, List<ValidationError<Object>>>())));
    }

    public static void create(Context ctx) {
        String name = ctx.formParamAsClass("name", String.class).get();
        String body = ctx.formParamAsClass("body", String.class).get();

        try {
            name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() > 2, "Название должно быть длиннее двух символов")
                    .get();

            Post post = new Post(name, body);
            PostRepository.save(post);
            PostsPage.setFlash("Post was successfully created!");
            ctx.redirect("/posts");

        }
        catch (ValidationException e) {
            BuildPostPage buildPostPage = new BuildPostPage(name, body, e.getErrors());
            ctx.render("posts/build.jte", model("page", buildPostPage));
        }
    }
    // END

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id)
            .orElseThrow(() -> new NotFoundResponse("Post not found"));

        var page = new PostPage(post);
        ctx.render("posts/show.jte", model("page", page));
    }
}
