package exercise;

import io.javalin.Javalin;

import java.util.List;

import io.javalin.http.NotFoundResponse;
import exercise.model.User;
import exercise.dto.users.UserPage;
import exercise.dto.users.UsersPage;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.rendering.template.JavalinJte;

public final class App {

    private static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.before(ctx -> {
            ctx.header("Content-Type", "text/html; charset=UTF-8");
        });

        app.get("/users/{id}", ctx -> {
            var id = ctx.pathParamAsClass("id", Long.class).get();

            User user = USERS.stream()
                    .filter(u -> id.equals(u.getId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("User not found"));

            var page = new UserPage(user);
            ctx.render("users/show.jte", model("page", page));
        });

        app.get("/users", ctx -> {
            var page = new UsersPage(USERS);
            ctx.render("users/index.jte", model("USERS", USERS));

        });
        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
