package exercise;

import io.javalin.Javalin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.model.User;
import io.javalin.rendering.template.JavalinJte;

public final class App {

    private static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // Handling the "/users" route
        app.get("/users", ctx -> {
            String term = ctx.queryParam("term");
            List<User> filteredUsers = USERS;

            if (term != null && !term.isEmpty()) {
                filteredUsers = USERS.stream()
                        .filter(user -> user.getFirstName().toLowerCase().contains(term.toLowerCase()))
                        .toList();
            }

            // Prepare the data map for the index.jte template
            Map<String, Object> data = new HashMap<>();
            data.put("USERS", filteredUsers);   // List<User>
            data.put("term", term);              // String term

            // Render the page with the index.jte content, passing all the required data
            ctx.render("index.jte", data);  // Render index.jte with filtered users
        });

        // Root route ("/") for a simple welcome page
        app.get("/", ctx -> {
            ctx.result("Hello");
        });

        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
