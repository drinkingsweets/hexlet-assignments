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

    // Каждый пользователь представлен объектом класса User
    public static final List<User> USERS = Data.getUsers();

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        // BEGIN
        // END

        app.get("/users", ctx -> {
    ctx.render("users/index.jte", model("USERS", USERS));
        });

        app.get("/users/{id}", ctx -> {
            boolean found = false;
            long id = ctx.pathParamAsClass("id", Long.class).get();
            for(User user: USERS){
                if (user.getId() == id){
                    found = true;
                    ctx.render("users/show.jte", model("user", user));
                }
            }
            if (!found){
                ctx.status(404).result("User not found");
            }
        });


        return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
