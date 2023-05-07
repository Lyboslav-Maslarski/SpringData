package UserSystem.entities;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordConstraintsValidator.class)
public @interface Password {
    String message() default "Invalid password!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minLength();

    int maxLength();

    boolean containsDigit();

    boolean containsLowerCase();

    boolean containsUpperCase();

    boolean containsSpecialSymbols();
}
