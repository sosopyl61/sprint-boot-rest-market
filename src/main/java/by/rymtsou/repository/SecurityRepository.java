package by.rymtsou.repository;

import by.rymtsou.config.DatabaseConfig;
import by.rymtsou.config.SQLQuery;
import by.rymtsou.model.Security;
import by.rymtsou.model.dto.RegistrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Optional;

@Repository
public class SecurityRepository {

    private final DatabaseConfig databaseConfig;

    @Autowired
    public SecurityRepository(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Optional<Security> getSecurityById(Long id) {
        Connection connection = databaseConfig.getConnection();

        try {
            PreparedStatement getSecurityStatement = connection.prepareStatement(SQLQuery.GET_SECURITY_BY_ID);
            getSecurityStatement.setLong(1, id);

            ResultSet resultSet = getSecurityStatement.executeQuery();
            return parseSecurity(resultSet);
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Boolean registration(RegistrationRequestDto requestDto) throws SQLException {
        Connection connection = databaseConfig.getConnection();

        try {
            connection.setAutoCommit(false);
            PreparedStatement createUserStatement = connection.prepareStatement(SQLQuery.CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, requestDto.getFirstname());
            createUserStatement.setString(2, requestDto.getSecondName());
            createUserStatement.setInt(3, requestDto.getAge());
            createUserStatement.setString(4, requestDto.getTelephoneNumber());
            createUserStatement.setString(5, requestDto.getEmail());
            createUserStatement.setString(6, requestDto.getSex());
            createUserStatement.executeUpdate();

            ResultSet generatedKeys = createUserStatement.getGeneratedKeys();
            long userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getLong(1);
            }

            PreparedStatement createSecurityStatement = connection.prepareStatement(SQLQuery.CREATE_SECURITY);
            createSecurityStatement.setString(1, requestDto.getLogin());
            createSecurityStatement.setString(2, requestDto.getPassword());
            createSecurityStatement.setLong(3, userId);
            createSecurityStatement.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
        }
        return false;
    }

    public Boolean isLoginUsed(String login) {
        Connection connection = databaseConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(SQLQuery.GET_SECURITY_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("login").equals(login)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    public Boolean updateSecurity(Security security) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement updateSecurityStatement = connection.prepareStatement(SQLQuery.UPDATE_SECURITY);
            updateSecurityStatement.setString(1, security.getLogin());
            updateSecurityStatement.setString(2, security.getPassword());
            updateSecurityStatement.setLong(3, security.getId());

            return updateSecurityStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public Boolean deleteSecurity(Long id) {
        try (Connection connection = databaseConfig.getConnection()) {
            PreparedStatement deleteSecurityStatement = connection.prepareStatement(SQLQuery.DELETE_SECURITY);
            deleteSecurityStatement.setLong(1, id);

            int rowsAffected = deleteSecurityStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private Optional<Security> parseSecurity (ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Security security = new Security();
            security.setId(resultSet.getLong("id"));
            security.setLogin(resultSet.getString("login"));
            security.setPassword(resultSet.getString("password"));
            security.setCreated(resultSet.getTimestamp("created"));
            security.setUpdated(resultSet.getTimestamp("updated"));
            security.setUserId(resultSet.getLong("user_id"));
            return Optional.of(security);
        }
        return Optional.empty();
    }
}
