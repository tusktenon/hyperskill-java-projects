package cinema;

import cinema.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class SeatingController {

    private final SeatingService seatingService;
    private final String managerPassword;

    public SeatingController(SeatingService seatingService, String managerPassword) {
        this.seatingService = seatingService;
        this.managerPassword = managerPassword;
    }

    @GetMapping("/seats")
    public SeatingPlan seats() {
        return seatingService.seatingPlan();
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats(@RequestParam(required = false) String password) {
        return Objects.equals(password, managerPassword)
                ? ResponseEntity.ok().body(seatingService.statistics())
                : ResponseEntity.status(401).body(new ErrorMessage("The password is wrong!"));
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
