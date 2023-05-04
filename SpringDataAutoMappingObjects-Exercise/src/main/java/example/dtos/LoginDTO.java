package example.dtos;

import example.exceptions.ValidationException;

import java.util.regex.Pattern;

public class LoginDTO {
    private static final Pattern PATTERN_AT = Pattern.compile("@");
    private static final Pattern PATTERN_DOT = Pattern.compile("[.]");
    private final String email;
    private final String password;

    public LoginDTO(String[] commandParts) {
        this.email = commandParts[1];
        this.password = commandParts[2];

        validate();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    private void validate() {
        if (!PATTERN_AT.matcher(email).find() || !PATTERN_DOT.matcher(email).find()) {
            throw new ValidationException("Email must contain '@' and '.'.");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 symbols.");
        }
    }
}
