package dataserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;
import java.util.random.RandomGenerator;

@RestController
public class Controller {

    private final int id;
    private final long delay;
    private final boolean randomize;
    private final Logger logger;
    private final RandomGenerator rng = RandomGenerator.getDefault();
    private final TransactionService transactionService;

    public Controller(
            @Value("${dataservice.id}") int id,
            @Value("${dataservice.delay}") long delay,
            @Value("${dataservice.random}") boolean randomize
    ) {
        this.id = id;
        this.delay = delay;
        this.randomize = randomize;
        this.logger = Logger.getLogger("Transaction server " + id);
        this.transactionService = new TransactionService(id);
    }

    @GetMapping("/ping")
    public String ping() {
        return "Pong from Server " + id;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> allTransactions(
            @RequestParam(defaultValue = "02248") String account
    ) {
        logger.info("Received request for account " + account);
        if (delay > 0) {
            logger.info("Sleeping for " + delay + " milliseconds");
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
        }
        if (randomize) {
            switch (rng.nextInt(3)) {
                case 0 -> {
                    logger.info("Responding with 429 TOO MANY REQUESTS");
                    return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
                }
                case 1 -> {
                    logger.info("Responding with 503 SERVICE UNAVAILABLE");
                    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
                }
                default -> {
                    logger.info("Responding with 200 OK");
                    return ResponseEntity.ok()
                            .body(transactionService.transactionsByAccount(account));
                }
            }
        }
        logger.info("Responding with 200 OK");
        return ResponseEntity.ok().body(transactionService.transactionsByAccount(account));
    }
}
