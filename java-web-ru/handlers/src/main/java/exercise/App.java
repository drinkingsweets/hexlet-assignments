package exercise;

import io.javalin.Javalin;

public final class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
        });
        app.get("/phones", ctx -> ctx.json(Data.getPhones().toString()));
        app.get("domains", ctx -> ctx.json(Data.getDomains().toString()));
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
