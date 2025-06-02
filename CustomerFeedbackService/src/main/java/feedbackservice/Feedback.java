package feedbackservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public record Feedback(
        @Id String id,
        int rating,
        String feedback,
        String customer,
        String product,
        String vendor
) {}
