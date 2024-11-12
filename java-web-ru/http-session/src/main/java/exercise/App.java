package exercise;

import io.javalin.Javalin;
import java.util.List;
import java.util.Map;

public final class App {

    private static final List<Map<String, String>> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });

        // BEGIN
        List<Map<String, String>> users = Data.getUsers();
        app.get("/users", ctx -> {
                  int page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
                  int per = ctx.queryParamAsClass("per", Integer.class).getOrDefault(5);
                  List<Map<String, String>> reply = users.subList((page - 1) * per, Math.min(page * per, users.size()));
                  ctx.json(reply);
                });

        // END

        return app;

    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
