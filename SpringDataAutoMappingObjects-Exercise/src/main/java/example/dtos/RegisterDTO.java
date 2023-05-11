package example.dtos;


import example.exceptions.ValidationException;

import java.util.regex.Pattern;

public class RegisterDTO {
    private static final Pattern PATTERN_LOWER = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGIT = Pattern.compile("\\d");
    private static final Pattern PATTERN_AT = Pattern.compile("@");
    private static final Pattern PATTERN_DOT = Pattern.compile("[.]");
    private final String email;
    private final String password;
    private final String confirmedPassword;
    private final String fullName;


    public RegisterDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];
        this.confirmedPassword = commandParts[3];
        this.fullName = commandParts[4];

        validate();
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public String getEmail() {
        return email;
    }

    private void validate() {

        if (!PATTERN_AT.matcher(email).find() || !PATTERN_DOT.matcher(email).find()) {
            throw new ValidationException("Email must contain '@' and '.'.");
        }

        if (!password.equals(confirmedPassword)) {
            throw new ValidationException("Password and confirm password must match.");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 symbols.");
        }

        if (!PATTERN_LOWER.matcher(password).find() | !PATTERN_UPPER.matcher(password).find() | !PATTERN_DIGIT.matcher(password).find()) {
            throw new ValidationException("Password must contain at least 1 lower case letter, one upper case letter and one digit.");
        }
    }
}
