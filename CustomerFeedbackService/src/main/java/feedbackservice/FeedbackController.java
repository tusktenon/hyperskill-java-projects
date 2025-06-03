package feedbackservice;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
public class FeedbackController {

    public static final int PER_PAGE_DEFAULT = 10;
    public static final int PER_PAGE_MIN = 5;
    public static final int PER_PAGE_MAX = 20;

    private final FeedbackRepository repository;

    public FeedbackController(FeedbackRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/feedback")
    public FeedbackPageDTO getAllFeedback(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {
        page = Math.max(page, 1);
        if (perPage < PER_PAGE_MIN || perPage > PER_PAGE_MAX) perPage = PER_PAGE_DEFAULT;
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by("id").descending());
        return new FeedbackPageDTO(repository.findAll(pageable));
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
