package aggregator;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Comparator;
import java.util.List;

@RestController
public class Aggregator {

    private static final int MAX_RETRIES = 5;

    private final List<String> sourceURIs;

    public Aggregator(List<String> sourceURIs) {
        this.sourceURIs = sourceURIs;
    }

    @GetMapping("/aggregate")
    public List<Transaction> aggregate(@RequestParam String account) {
        return sourceURIs.stream()
                .flatMap(uri -> getTransactions(uri, account).stream())
                .sorted(Comparator.comparing(Transaction::timestamp).reversed())
                .toList();
    }

    private List<Transaction> getTransactions(String baseURI, String account) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            // As of Spring 6.1, RestClient is preferred to RestTemplate
            try {
                return RestClient.create(baseURI + "/transactions?account=" + account)
                        .get()
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<Transaction>>() {});
            } catch (RestClientResponseException ignored) {
            }
        }
        return List.of();
    }
}
