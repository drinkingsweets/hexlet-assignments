package exercise.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import exercise.model.Product;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ProductsRepository extends BaseRepository {

    // BEGIN
    public static void save(Product product) {
        // Убираем id из запроса, так как id будет автоматически сгенерирован
        String query = "INSERT INTO products (title, price) VALUES (?, ?);";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setInt(2, product.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving product: " + e.getMessage(), e);
        }
    }

    public static Optional<Product> find(long id) {
        String query = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, id); // Set the id parameter
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id2 = resultSet.getLong("id");
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Product product = new Product(title, price);
                product.setId(id2);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding product: " + e.getMessage(), e);
        }
        return Optional.empty(); // Return an empty Optional if not found
    }

    public static List<Product> getEntities() {
        List<Product> entities = new ArrayList<>();
        String query = "SELECT * FROM products;";
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                int price = resultSet.getInt("price");
                Product product = new Product(title, price);
                product.setId(id);
                entities.add(product);
            }
            return entities;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving products: " + e.getMessage(), e);
        }
    }
    // END
}
