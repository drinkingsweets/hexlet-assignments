package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;

import exercise.dto.posts.EditPostPage;
import exercise.dto.posts.PostsPage;
import exercise.dto.posts.PostPage;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.dto.posts.BuildPostPage;
import exercise.util.NamedRoutes;

import io.javalin.http.Context;
import io.javalin.validation.ValidationException;
import io.javalin.http.NotFoundResponse;

public class PostsController {

    public static void build(Context ctx) {
        var page = new BuildPostPage();
        ctx.render("posts/build.jte", model("page", page));
    }

    public static void create(Context ctx) {
        try {
            var name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() >= 2, "Название не должно быть короче двух символов")
                    .get();

            var body = ctx.formParamAsClass("body", String.class)
                    .check(value -> value.length() >= 10, "Пост должен быть не короче 10 символов")
                    .get();

            var post = new Post(name, body);
            PostRepository.save(post);
            ctx.redirect(NamedRoutes.postsPath());

        } catch (ValidationException e) {
            var name = ctx.formParam("name");
            var body = ctx.formParam("body");
            var page = new BuildPostPage(name, body, e.getErrors());
            ctx.render("posts/build.jte", model("page", page)).status(422);
        }
    }

    public static void index(Context ctx) {
        var posts = PostRepository.getEntities();
        var postPage = new PostsPage(posts);
        ctx.render("posts/index.jte", model("page", postPage));
    }

    public static void show(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Post not found"));

        var page = new PostPage(post);
        ctx.render("posts/show.jte", model("page", page));
    }

    // BEGIN
    public static void showEdit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var post = PostRepository.find(id);
        EditPostPage editPostPage = new EditPostPage(post.get());

        ctx.render("posts/edit.jte", model("page", editPostPage));


    }

    public static void makeEdit(Context ctx) {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        Post old = PostRepository.find(id).get();

        try {
            var name = ctx.formParamAsClass("name", String.class)
                    .check(value -> value.length() >= 2, "Название не должно быть короче двух символов")
                    .get();

            var body = ctx.formParamAsClass("body", String.class)
                    .check(value -> value.length() >= 10, "Пост должен быть не короче 10 символов")
                    .get();

            var post = new Post(name, body);
            post.setId(old.getId());
            PostRepository.getEntities().set(PostRepository.getEntities().indexOf(old), post);
            ctx.redirect(NamedRoutes.postsPath());

        } catch (ValidationException e) {
            long oldId = ctx.pathParamAsClass("id", Long.class).get();
            Post oldPost = PostRepository.find(id).get();

            String name = ctx.formParamAsClass("name", String.class).get();
            String body = ctx.formParamAsClass("body", String.class).get();

            oldPost.setName(name);
            old.setBody(body);

            var page = new EditPostPage(oldPost, e.getErrors());
            ctx.render("posts/edit.jte", model("page", page)).status(422);
        }
    }
    // END
}
