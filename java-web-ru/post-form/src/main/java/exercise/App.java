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

    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input; // Если строка пуста или null, возвращаем как есть
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

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

            // Получаем параметры формы с удалением лишних пробелов
            String firstName = ctx.formParam("firstName") == null ? "" : ctx.formParam("firstName").trim();
            String lastName = ctx.formParam("lastName") == null ? "" : ctx.formParam("lastName").trim();
            String email = ctx.formParam("email") == null ? "" : ctx.formParam("email").trim().toLowerCase();
            String password = ctx.formParam("password") == null ? "" : ctx.formParam("password").trim();

            // Проверяем обязательные параметры
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                ctx.status(400).result("Invalid form data");
                return;
            }

            // Сохраняем пользователя
            UserRepository.save(new User(
                    capitalize(firstName),
                    capitalize(lastName),
                    email,
                    Security.encrypt(password)
            ));

            // Перенаправляем пользователя на страницу списка
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
