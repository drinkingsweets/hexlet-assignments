package exercise.controller;

import org.apache.commons.lang3.StringUtils;
import exercise.util.Security;
import exercise.model.User;
import exercise.util.NamedRoutes;
import static io.javalin.rendering.template.TemplateUtil.model;
import exercise.repository.UserRepository;
import exercise.dto.users.UserPage;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.Context;

import java.util.Optional;


public class UsersController {

    public static void build(Context ctx) throws Exception {
        ctx.render("users/build.jte");
    }

    // BEGIN
    public static void register(Context ctx) {
        String firstName = ctx.formParamAsClass("firstName", String.class).get();
        String lastName = ctx.formParamAsClass("lastName", String.class).get();
        String email = ctx.formParamAsClass("email", String.class).get();
        String password = ctx.formParamAsClass("password", String.class).get();

        String token = Security.generateToken();

        UserRepository.save(new User(firstName, lastName, email, password, token));
        ctx.cookie("token", token);
        long id = UserRepository.getEntities()
        .stream()
        .filter(user -> user.getToken().equals(token)) // Фильтруем пользователей по токену
        .map(User::getId) // Преобразуем пользователя в его ID
        .findFirst() // Берем первый найденный ID
        .orElseThrow(() -> new IllegalArgumentException("Пользователь с таким токеном не найден"));

        ctx.redirect("/users/" + id);
    }

    public static void show(Context ctx) {
        String token = ctx.cookie("token");
        Optional<User> user = UserRepository.find(ctx.pathParamAsClass("id", Long.class).get());
        if (user.isPresent() && token.equals(user.get().getToken())) {
            UserPage userPage = new UserPage(user.get());
            ctx.render("users/show.jte", model("page", userPage));
        } else {
            ctx.redirect("/users/build");
        }
    }
    // END
}
