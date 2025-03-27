package by.rymtsou.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Scope("prototype")
public class User {
    private Long id;
    private String firstname;
    private String secondName;
    private Integer age;
    private String telephoneNumber;
    private String email;
    private Timestamp created;
    private Timestamp updated;
    private String sex;
    private Boolean deleted;
}