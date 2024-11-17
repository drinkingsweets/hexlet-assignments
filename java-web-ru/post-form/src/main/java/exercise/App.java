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
    // Извлечение параметров формы
    String firstName = ctx.formParam("firstName");
    String secondName = ctx.formParam("secondName");
    String email = ctx.formParam("email");
    String password = ctx.formParam("password");


    // Удаляем лишние пробелы и обрабатываем строку
    email = email.toLowerCase().trim();
    firstName = firstName.trim();
    secondName = secondName.trim();
    password = password.trim();

    // Сохранение пользователя
    User newUser = new User(
        firstName.toUpperCase(),
        secondName.toUpperCase(),
        email,
        Security.encrypt(password)
    );
    UserRepository.save(newUser);

    // Перенаправление на список пользователей после успешного добавления
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
