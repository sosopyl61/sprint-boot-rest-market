package by.rymtsou.service.validation;

import by.rymtsou.annotation.CustomAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeValidator implements ConstraintValidator<CustomAge, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value != null && value >= 18 && value <= 120;
    }
}
