package exercise;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.SQLException;

import java.util.stream.Collectors;

import exercise.controller.ProductsController;
import exercise.controller.RootController;
import exercise.util.NamedRoutes;
import exercise.repository.BaseRepository;

import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.javalin.rendering.template.JavalinJte;


@Slf4j
public final class App {

    public static Javalin getApp() throws IOException, SQLException {

        var hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:hexlet;DB_CLOSE_DELAY=-1;");

        var dataSource = new HikariDataSource(hikariConfig);
        var url = App.class.getClassLoader().getResourceAsStream("schema.sql");
        var sql = new BufferedReader(new InputStreamReader(url))
            .lines().collect(Collectors.joining("\n"));

        log.info(sql);
        try (var connection = dataSource.getConnection();
                var statement = connection.createStatement()) {
            statement.execute(sql);
        }
        BaseRepository.dataSource = dataSource;

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableDevLogging();
            config.fileRenderer(new JavalinJte());
        });

        app.get(NamedRoutes.rootPath(), RootController::index);
        app.get(NamedRoutes.buildProductPath(), ProductsController::build);
        app.post(NamedRoutes.productsPath(), ProductsController::create);
        app.get(NamedRoutes.productsPath(), ProductsController::index);
        app.get(NamedRoutes.productPath("{id}"), ProductsController::show);

        return app;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Javalin app = getApp();
        app.start(7070);
    }
}
