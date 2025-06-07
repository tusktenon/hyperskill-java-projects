package aggregator;

import org.springframework.beans.factory.annotation.Value;
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

    private final RestClient client;

    @Value("${aggregator.sources}")
    private List<String> sourceURLs;

    public AggregationService(RestClient client) {
        this.client = client;
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
        return (CompletableFuture<List<Transaction>>[]) sourceURLs.stream()
                .map(url -> getTransactionsAsync(url, account))
                .toArray(CompletableFuture[]::new);
    }

    @Async
    public CompletableFuture<List<Transaction>> getTransactionsAsync(String url, String account) {
        return CompletableFuture.supplyAsync(() -> getTransactions(url, account));
    }

    private List<Transaction> getTransactions(String url, String account) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            // As of Spring 6.1, RestClient is preferred to RestTemplate
            try {
                return client.get()
                        .uri(url + "/transactions?account=" + account)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(new ParameterizedTypeReference<List<Transaction>>() {});
            } catch (RestClientResponseException ignored) {
            }
        }
        return List.of();
    }
}
