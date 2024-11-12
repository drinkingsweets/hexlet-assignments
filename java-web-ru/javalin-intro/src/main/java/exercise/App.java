package exercise;

// BEGIN
import io.javalin.Javalin;
// END

public final class App {

    public static Javalin getApp() {
        var app = Javalin.create(config -> config.bundledPlugins.enableDevLogging());
        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/welcome", ctx -> ctx.result("Welcome to Hexlet!"));
        app.start(7070);
        // Стартуем веб-сервер
        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
