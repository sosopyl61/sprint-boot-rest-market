package by.rymtsou.repository;

import by.rymtsou.config.DatabaseConfig;
import by.rymtsou.config.SQLQuery;
import by.rymtsou.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final DatabaseConfig databaseConfig;

    @Autowired
    public ProductRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Optional<Product> getProductById(Long id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_PRODUCT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            return parseProduct(resultSet);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Optional<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_ALL_PRODUCTS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCreated(resultSet.getTimestamp("created"));
                product.setUpdated(resultSet.getTimestamp("updated"));
                products.add(product);
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.of(products);
    }

    public Optional<Long> createProduct(Product product) {
        Long productId = null;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                productId = resultSet.getLong(1);
            }

            return Optional.of(productId);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Boolean updateProduct(Product product) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQLQuery.UPDATE_PRODUCT)) {

            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean deleteProduct(Long id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement deleteProductStatement = connection.prepareStatement(SQLQuery.DELETE_PRODUCT)) {
            deleteProductStatement.setLong(1, id);

            int rowsAffected = deleteProductStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private Optional<Product> parseProduct(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setCreated(resultSet.getTimestamp("created"));
            product.setUpdated(resultSet.getTimestamp("updated"));

            return Optional.of(product);
        }
        return Optional.empty();
    }
}
