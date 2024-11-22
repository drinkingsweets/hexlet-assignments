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
        String query = "INSERT INTO products (id, title, price) VALUES (?, ?, ?);";

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(query)) {
            preparedStatement.setLong(1, product.getId());
            preparedStatement.setString(2, product.getTitle());
            preparedStatement.setInt(3, product.getPrice());

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

    public static long nextIndex() {
        String query = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM products";

        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getLong("next_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching next course index", e);
        }

        throw new RuntimeException("Unable to determine next course index");
    }
    // END
}