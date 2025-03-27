package by.rymtsou.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
@Component
@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfig {
    public Environment environment;

    @Autowired
    public DatabaseConfig(Environment environment) {
        this.environment = environment;
    }

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    environment.getProperty("spring.datasource.url"),
                    environment.getProperty("spring.datasource.username"),
                    environment.getProperty("spring.datasource.password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
