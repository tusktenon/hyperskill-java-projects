package aggregator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@EnableAsync
public class AggregationService {

    private static final int MAX_RETRIES = 5;

    private final List<String> sourceURIs;

    public AggregationService(List<String> sourceURIs) {
        this.sourceURIs = sourceURIs;
    }

    @Cacheable("transactions")
    public List<Transaction> getSortedTransactions(String account) {
        var futures = getTransactionsAsync(account);
        CompletableFuture.allOf(futures).join();
        return Arrays.stream(futures)
                .flatMap(future -> future.join().stream())
                .sorted(Comparator.comparing(Transaction::timestamp).reversed())
                .toList();
    }

    @SuppressWarnings("unchecked")
    private CompletableFuture<List<Transaction>>[] getTransactionsAsync(String account) {
        return (CompletableFuture<List<Transaction>>[]) sourceURIs.stream()
                .map(uri -> getTransactionsAsync(uri, account))
                .toArray(CompletableFuture[]::new);
    }

    @Async
    public CompletableFuture<List<Transaction>> getTransactionsAsync(String uri, String account) {
        return CompletableFuture.supplyAsync(() -> getTransactions(uri, account));
    }

    private List<Transaction> getTransactions(String uri, String account) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            // As of Spring 6.1, RestClient is preferred to RestTemplate
            try {
                return RestClient.create(uri + "/transactions?account=" + account)
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
