package cinema;

import cinema.models.SeatingPlan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeatingController {

    @GetMapping("/seats")
    public SeatingPlan seats() {
        return new SeatingPlan(9, 9);
    }
}
