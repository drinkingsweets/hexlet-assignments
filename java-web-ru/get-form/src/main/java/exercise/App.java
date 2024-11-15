package exercise;

import gg.jte.html.HtmlContent;
import gg.jte.html.HtmlTemplateOutput;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exercise.model.User;
import io.javalin.rendering.template.JavalinJte;
import org.eclipse.jetty.server.HttpInput;

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
            if (term != null) {
                data.put("USERS", USERS.stream().filter(user -> user.
                        getFirstName().toLowerCase().contains(term.toLowerCase())).toList());
                data.put("term", term);
                data.put("content", "");
            } else {
                data.put("USERS", USERS);
                data.put("term", "");
                data.put("content", "");


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
