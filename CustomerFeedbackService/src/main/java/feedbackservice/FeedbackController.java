package feedbackservice;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class FeedbackController {

    private final FeedbackRepository repository;

    public FeedbackController(FeedbackRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> addFeedback(@Valid @RequestBody Feedback feedback) {
        String id = repository.save(feedback).id();
        return ResponseEntity.created(URI.create("/feedback/" + id)).build();
    }
}
