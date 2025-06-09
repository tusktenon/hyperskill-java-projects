package aggregator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
public class AggregationService {

    private static final int MAX_RETRIES = 5;

    private final WebClient client;

    @Value("${aggregator.sources}")
    private List<String> sourceURLs;

    public AggregationService(WebClient client) {
        this.client = client;
    }

    @Cacheable("transactions")
    public Mono<List<Transaction>> getSortedTransactions(String account) {
        return Flux.merge(getTransactions(account))
                .collectSortedList(Comparator.comparing(Transaction::timestamp).reversed());
    }

    @SuppressWarnings("unchecked")
    private Flux<Transaction>[] getTransactions(String account) {
        return (Flux<Transaction>[]) sourceURLs.stream()
                .map(url -> getTransactions(url, account))
                .toArray(Flux[]::new);
    }

    public Flux<Transaction> getTransactions(String url, String account) {
        return client.get()
                .uri(url + "/transactions?account=" + account)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Transaction.class)
                .retry(MAX_RETRIES)
                .onErrorComplete();
    }
}
