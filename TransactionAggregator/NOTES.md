# Transaction Aggregator: Notes

## Stage 3/4: Faulty servers

The instructions are vague/misleading as to how you should handle the case where a data server responds to all 5 attempts with an error code. To pass Hyperskill's tests, your program should treat this situation as if the server responded with an empty list.

You may assume a data server always responds with `200 OK`, `429 TOO MANY REQUESTS` or `503 SERVICE UNAVAILABLE`.


## Stage 4/4: Long queries

I completed all four stages using [`RestClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestClient.html), but there's a strong case for switching to [`WebClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html) for Stages 3 and 4. `RestClient` and `WebClient` have similar APIs, but the latter performs non-blocking (asynchronous) requests. Moreover, `WebClient` includes a `retry()` method that simplifies the solution to Stage 3. (More precisely, `retry()` methods are provided by the [`Mono<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html) and [`Flux<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) classes used as return types by the relevant `WebClient` methods.) My `WebClient`-based solution can be found [here](../TransactionAggregatorWebClient).
