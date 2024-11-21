package exercise.controller;

import static io.javalin.rendering.template.TemplateUtil.model;
import exercise.dto.MainPage;
import exercise.dto.LoginPage;
import exercise.model.User;
import exercise.repository.UsersRepository;
import static exercise.util.Security.encrypt;

import exercise.util.Security;
import io.javalin.http.Context;

import java.util.Optional;

public class SessionsController {

    // BEGIN
    public static void show(Context ctx) {
        String currentName = ctx.sessionAttribute("currentUser");
        ctx.render("index.jte", model("currentUser", currentName));
    }

    public static void loginPage(Context ctx) {
        ctx.render("build.jte", model("page", null));
    }

    public static void auth(Context ctx) {
        String name = ctx.formParamAsClass("name", String.class).get();
        String password = ctx.formParamAsClass("password", String.class).get();

        Optional<User> user = UsersRepository.findByName(name);
        if (user.isPresent() && user.get().getPassword().equals(Security.encrypt(password))) {
            ctx.sessionAttribute("currentUser", user.get().getName());
            ctx.redirect("/");
        }
        else {
            ctx.render("build.jte", model("page", new LoginPage(name, "Wrong username or password")));
        }
    }

    public static void logout(Context ctx) {
        ctx.sessionAttribute("currentUser", null);
        ctx.redirect("/");
    }
    // END
}
