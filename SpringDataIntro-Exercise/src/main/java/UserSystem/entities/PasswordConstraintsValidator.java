package UserSystem.entities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {
    private static final Pattern PATTERN_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGIT = Pattern.compile("\\d");
    private static final Pattern PATTERN_SYMBOL = Pattern.compile("[!@#$%^&*()_+<>?]");
    private int minLength;
    private int maxLength;
    private boolean hasUpper;
    private boolean hasLower;
    private boolean hasDigit;
    private boolean hasSpecialSymbol;

    @Override
    public void initialize(Password password) {
        this.minLength = password.minLength();
        this.maxLength = password.maxLength();
        this.hasUpper = password.containsUpperCase();
        this.hasLower = password.containsLowerCase();
        this.hasDigit = password.containsDigit();
        this.hasSpecialSymbol = password.containsSpecialSymbols();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.length() < this.minLength) {
            return false;
        }

        if (value.length() > this.maxLength) {
            return false;
        }

        if (this.hasLower && !PATTERN_LOWER.matcher(value).find()) {
            return false;
        }

        if (this.hasUpper && !PATTERN_UPPER.matcher(value).find()) {
            return false;
        }

        if (this.hasDigit && !PATTERN_DIGIT.matcher(value).find()) {
            return false;
        }

        if (this.hasSpecialSymbol && !PATTERN_SYMBOL.matcher(value).find()) {
            return false;
        }

        return true;
    }
}
