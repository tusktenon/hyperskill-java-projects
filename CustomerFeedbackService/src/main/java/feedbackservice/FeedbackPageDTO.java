package feedbackservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.List;

public record FeedbackPageDTO(
        @JsonProperty("total_documents")
        long totalDocuments,

        @JsonProperty("is_first_page")
        boolean isFirstPage,

        @JsonProperty("is_last_page")
        boolean isLastPage,

        List<Feedback> documents
) {
    public FeedbackPageDTO(Page<Feedback> page) {
        this(page.getTotalElements(), page.isFirst(), page.isLast(), page.getContent());
    }
}
