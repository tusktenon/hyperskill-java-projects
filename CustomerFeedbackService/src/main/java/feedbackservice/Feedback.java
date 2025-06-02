package feedbackservice;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public record Feedback(
        @Id String id,
        @Min(1) @Max(5) int rating,
        String feedback,
        String customer,
        @NotBlank String product,
        @NotBlank String vendor
) {}
