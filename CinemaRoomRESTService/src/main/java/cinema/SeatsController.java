package cinema;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatsController {

    @GetMapping("/seats")
    public SeatingPlan seats() {
        return new SeatingPlan(9, 9);
    }
}
