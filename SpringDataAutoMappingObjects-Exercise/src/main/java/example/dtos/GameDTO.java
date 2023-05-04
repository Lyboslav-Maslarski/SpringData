package example.dtos;

import example.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GameDTO {
    private final String title;
    private final String trailerId;
    private final String thumbnailUrl;
    private final float size;
    private final BigDecimal price;
    private final String description;
    private final LocalDate releaseDate;

    public GameDTO(String... args) {
        this.title = args[1];
        this.price = new BigDecimal(args[2]);
        this.size = Float.parseFloat(args[3]);
        this.trailerId = args[4];
        this.thumbnailUrl = args[5];
        this.description = args[6];
        this.releaseDate = LocalDate.parse(args[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        validate();
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public float getSize() {
        return size;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    private void validate() {
        if (title.length() < 3 || title.length() > 100) {
            throw new ValidationException("Title must be between 3 and 100 symbols.");
        }

        if (!Character.isUpperCase(title.charAt(0))) {
            throw new ValidationException("Title must start with upper case letter.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Price must be a positive number.");
        }

        if (size < 0) {
            throw new ValidationException("Size must be a positive number.");
        }

        if (trailerId.length() != 11) {
            throw new ValidationException("Trailer id must be exactly 11 symbols.");
        }

        if (!thumbnailUrl.startsWith("http://") && !thumbnailUrl.startsWith("https://")) {
            throw new ValidationException("Wrong thumbnail format.");
        }

        if (description.length() < 20) {
            throw new ValidationException("Description should be at least 20 symbols.");
        }
    }
}
