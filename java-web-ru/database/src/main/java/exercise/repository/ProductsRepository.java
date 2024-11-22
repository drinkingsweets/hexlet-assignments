package exercise.repository;

import exercise.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepository extends BaseRepository {

    // Метод для сохранения нового товара
    public static void save(Product product) throws SQLException {
        String query = "INSERT INTO products (title, price) VALUES (?, ?)";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getTitle());
            statement.setInt(2, product.getPrice());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                }
            }
        }
    }

    // Метод для поиска товара по его идентификатору
    public static Optional<Product> find(Long id) throws SQLException {
        String query = "SELECT * FROM products WHERE id = ?";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToProduct(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    // Метод для получения всех товаров
    public static List<Product> getEntities() throws SQLException {
        String query = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                products.add(mapToProduct(resultSet));
            }
        }
        return products;
    }

    // Вспомогательный метод для маппинга результата SQL-запроса в объект Product
    private static Product mapToProduct(ResultSet resultSet) throws SQLException {
        var product = new Product(
            resultSet.getString("title"),
            resultSet.getInt("price")
        );
        product.setId(resultSet.getLong("id"));
        return product;
    }
}
