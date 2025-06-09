package aggregator;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class AggregationController {

    private final AggregationService aggregationService;

    public AggregationController(AggregationService aggregationService) {
        this.aggregationService = aggregationService;
    }

    @GetMapping("/aggregate")
    public Mono<List<Transaction>> aggregate(@RequestParam String account) {
        return aggregationService.getSortedTransactions(account);
    }
}
