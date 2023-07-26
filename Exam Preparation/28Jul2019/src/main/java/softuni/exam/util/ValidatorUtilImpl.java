package softuni.exam.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
public class ValidatorUtilImpl implements ValidatorUtil {

    private final Validator validator;

    @Autowired
    public ValidatorUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {
        return violations(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E entity) {
        return validator.validate(entity);
    }
}