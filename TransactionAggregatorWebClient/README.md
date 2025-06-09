# Transaction Aggregator (WebClient implementation)

This project is an alternative implementation of the full (Stage 4) **Transaction Aggregator** project, using `WebClient` in place of `RestClient`.

I completed all four stages of Transaction Aggregator using Spring Web's[`RestClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestClient.html) to perform HTTP requests. That works just fine, but there's a strong case for switching to [`WebClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html) from Spring WebFlux for Stages 3 and 4:

- The [`Mono<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html) and [`Flux<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) types used by `WebClient` provide a `retry()` method, which makes it very easy to add the functionality required in Stage 3.
- `WebClient` makes non-blocking (asynchronous) HTTP requests, giving you half of Stage 4 "for free."
- At the same time, the switch to `WebClient` requires no changes in how you enable Spring's caching functionality.
