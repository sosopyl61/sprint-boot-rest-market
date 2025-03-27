package by.rymtsou;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "Spring Boot REST market",
        description = "This is a simple market application on Spring Boot with REST API.",
        contact = @Contact(
                name = "Illia Rymtsou",
                email = "rimtsov.ilya@gmail.com",
                url = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        )
))
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
