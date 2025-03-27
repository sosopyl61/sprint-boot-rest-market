package by.rymtsou.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope("prototype")
public class Product {
    private Long id;

    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive value")
    private Double price;

    @PastOrPresent(message = "Created timestamp cannot be in the future")
    private Timestamp created;

    @PastOrPresent(message = "Updated timestamp cannot be in the future")
    private Timestamp updated;
}
