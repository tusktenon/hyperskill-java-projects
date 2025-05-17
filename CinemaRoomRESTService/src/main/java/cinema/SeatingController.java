package cinema;

import cinema.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SeatingController {

    private final SeatingService seatingService;

    public SeatingController(SeatingService seatingService) {
        this.seatingService = seatingService;
    }

    @GetMapping("/seats")
    public SeatingPlan seats() {
        return seatingService.seatingPlan();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody Seat seat) {
        try {
            return ResponseEntity.ok().body(seatingService.purchase(seat));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }

    @PostMapping("/return")
    public ResponseEntity<?> refund(@RequestBody PurchaseToken purchase) {
        try {
            return ResponseEntity.ok().body(seatingService.refund(purchase));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
}
