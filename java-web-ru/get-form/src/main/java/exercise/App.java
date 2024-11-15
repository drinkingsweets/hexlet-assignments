package exercise;

import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.model.User;
import static io.javalin.rendering.template.TemplateUtil.model;
import io.javalin.rendering.template.JavalinJte;

import org.apache.commons.lang3.StringUtils;

public final class App {

    // Каждый пользователь представлен объектом класса User
    private static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // BEGIN
        app.get("/users", ctx -> {
            String term = ctx.queryParam("term");
            Map<String, Object> data = new HashMap<>();
            if (term != null){
                data.put("USERS", USERS.stream().filter(user -> user.
                            getFirstName().toLowerCase().equals(term.toLowerCase())).toList());
                data.put("term", term);
            }
            else {
                data.put("USERS", USERS);
                data.put("term", "");

            }
            ctx.render("layout/page.jte", data);

        });
        // END

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
