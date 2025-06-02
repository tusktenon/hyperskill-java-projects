package feedbackservice;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
public class FeedbackController {

    private final FeedbackRepository repository;

    public FeedbackController(FeedbackRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/feedback")
    public List<Feedback> getAllFeedback() {
        return repository.findAll(Sort.by("id").descending());
    }

    @GetMapping("/feedback/{id}")
    public Feedback getFeedbackById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/feedback")
    public ResponseEntity<Void> addFeedback(@Valid @RequestBody Feedback feedback) {
        String id = repository.save(feedback).id();
        return ResponseEntity.created(URI.create("/feedback/" + id)).build();
    }
}
