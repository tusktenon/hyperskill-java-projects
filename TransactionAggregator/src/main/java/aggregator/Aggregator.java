package aggregator;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;

@RestController
public class Aggregator {

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
        // As of Spring 6.1, RestClient is preferred to RestTemplate
        return RestClient.create(baseURI + "/transactions?account=" + account)
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Transaction>>() {});
    }
}
