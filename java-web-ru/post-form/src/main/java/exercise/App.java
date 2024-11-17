package exercise;

import io.javalin.Javalin;

import java.util.List;

import static io.javalin.rendering.template.TemplateUtil.model;

import io.javalin.rendering.template.JavalinJte;
import exercise.model.User;
import exercise.dto.users.UsersPage;
import exercise.repository.UserRepository;
import exercise.util.Security;

public final class App {

    public static Javalin getApp() {

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get("/", ctx -> {
            ctx.render("index.jte");
        });

        app.get("/users", ctx -> {
            List<User> users = UserRepository.getEntities();
            var page = new UsersPage(users);
            ctx.render("users/index.jte", model("page", page));
        });

        // BEGIN
        app.get("/users/build", ctx -> {
            ctx.render("users/build.jte");
        });

        app.post("/users", ctx -> {

            // Проверяем, содержится ли форма данных
            if (ctx.formParamMap().isEmpty()) {
                ctx.status(400).result("No form data submitted");
                return;
            }

            String firstName = ctx.formParam("firstName");
            String secondName = ctx.formParam("secondName");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            // Проверяем на наличие всех необходимых параметров
            if (email == null || firstName == null || secondName == null || password == null) {
                ctx.status(400).result("Invalid form data");
                return;
            }

            // Сохраняем пользователя
            UserRepository.save(new User(
                firstName.toUpperCase(),
                secondName.toUpperCase(),
                email.toLowerCase().trim(),
                Security.encrypt(password)
            ));

            // Перенаправляем пользователя на список
            ctx.redirect("/users");
        });

                // END

                return app;
    }

    public static void main(String[] args) {
        Javalin app = getApp();
        app.start(7070);
    }
}
