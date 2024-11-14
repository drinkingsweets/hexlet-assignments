package exercise;

import io.javalin.Javalin;
import exercise.model.User;
import exercise.dto.users.UserPage;
import exercise.dto.users.UsersPage;
import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.rendering.template.JavalinJte;

import java.util.List;
import java.util.Optional;

public final class App {

    // Constant with the list of users
    public static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {
        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // Handler for listing users
        app.get("/users", ctx -> {
            // Pass the list of users to the template
            ctx.render("users/index.jte", model("USERS", USERS));
        });

        // Handler for showing individual user details
        app.get("/users/{id}", ctx -> {
            // Get user ID from the path parameter
            long id = ctx.pathParamAsClass("id", Long.class).get();
            Optional<User> userOpt = USERS.stream()
                                          .filter(user -> user.getId() == id)
                                          .findFirst();

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Pass user data to the template for rendering
                ctx.render("users/show.jte", model("USER", user));
            } else {
                // If user not found, return 404 page
                ctx.status(404).render("users/404.jte");
            }
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
