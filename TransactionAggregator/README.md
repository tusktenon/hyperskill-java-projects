# Transaction Aggregator

## Project Description

Backend services often depends on other services to receive necessary data. In this project, you will design and implement a Transaction Aggregator system that demonstrates the fundamentals of inter-service communication using REST APIs. The project will also introduce you to the complexities of dealing with remote server errors, teaching you to build robust systems that can handle unexpected issues gracefully. You will learn how to implement retries to ensure system resilience and continuity of service even when remote servers are temporarily unavailable. Additionally, you'll gain experience in applying caching to optimize system performance and efficiency.

[View more](https://hyperskill.org/projects/424)


## Stage 1/4: Send a ping

### Description

In this stage, you will create a basic web service that will have a single endpoint. When a client sends a GET request to that endpoint, the service should connect to another server and return the response body it receives.

It's a simple but important step that will be the starting point for further development. You must be sure that you can successfully connect to a server before requesting actual data from it.

### Objectives

- Create the `GET /aggregate` endpoint that returns a plain text string to the client. When the client sends a request to this endpoint, the service should connect to another server at the address `http://localhost:8889` and send a GET request to its `/ping` endpoint:
    ```text
    URI         : http://localhost:8889/ping
    HTTP method : GET
    ```
    The server will respond with the status code `200 OK` and a string in the `text/plain` format. Your service should return that string to the client.
- At this stage the server you connect to will always send a valid response so you don't need to care about any error handling.

### Examples

**Example 1.** *GET request to the /aggregate endpoint:*

*Response code:* `200 OK`

*Response body:*
```text
Pong from server 1
```


## Stage 2/4: Fetch data

### Description

After successfully testing the connection, it's time to query some data from a remote data source.

There are two servers with the addresses `http://localhost:8888` and `http://localhost:8889.` Each of them has a `GET /transactions` endpoint that accepts a single query parameter `account`:
```text
GET http://localhost:8888/transactions?account=2844
```

Each server returns a JSON array of transaction data it stores related to the specified account. Each array item has the following structure:
```text
{
  "id": <string>,
  "serverId": <string>,
  "account": <string>,
  "amount": <string>,
  "timestamp": <string>
}
```

Your service should accept a request with an account number and fetch transaction data from both servers for that account. After that, you should combine the data into a single list, sort them by the timestamp so that the newer transactions come first, and return the resulting list to the client.

### Objectives

- Update the `GET /aggregate` endpoint. Now it should accept the `account` request parameter with the account number.

- Query the two remote services, `http://localhost:8888` and `http://localhost:8889`, for data using the `/transactions` endpoint and the account number as the query parameter:
    ```text
    URI         : http://localhost:8889/transactions?account=12345
    HTTP method : GET
    ```
    Each server will return a JSON array of transaction data or an empty array if no data is found.

- Join the two arrays (or lists), sort them by the transaction timestamp in the descending order and return the resulting array (or list) to the client.

### Examples

**Example 1.** *GET request to the /aggregate?account=02248 endpoint:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id":"31969aef-ffbe-413a-8a94-bc920556a0d4",
    "serverId":"server-04",
    "account":"02248",
    "amount":"5120",
    "timestamp":"2023-12-24T00:02:31.886783206"
  },
  {
    "id":"dcc57df0-d815-497f-be1d-b3fb419b9bee",
    "serverId":"server-04",
    "account":"02248",
    "amount":"4933",
    "timestamp":"2023-12-21T10:33:56.886823126"
  },
  {
    "id":"398c135b-b055-4415-a7be-4beb4a3c7da8",
    "serverId":"server-25",
    "account":"02248",
    "amount":"1205",
    "timestamp":"2023-12-19T16:56:48.886729416"
  }
]
```


## Stage 3/4: Faulty servers

### Description

The transaction aggregator service is performing its function, but recently the transaction data servers started to return errors. Sometimes instead of requested transaction data they return the status code `429 TOO MANY REQUESTS` and sometimes they respond with the status code `503 SERVICE UNAVAILABLE`.

The transaction aggregator service needs to handle such situations. It was noticed that the servers don't return errors for long, so after a small number of retries they respond with data. This means that you can apply a simple retry pattern:

1. Send a request.
2. Check if the response code is a server error.
3. If yes, send another request until the total number of retries reaches 5.
4. If no, return the received data.

Real life scenarios need more sophisticated approaches such as retries with exponential backoff and jitter, but in this case a simple series of retries will do the job.

### Objectives

- Update the logic of sending requests and receiving responses from the remote services `http://localhost:8888` and `http://localhost:8889`. These services may return a server error with a code of 429 or 503. Make up to 5 retries to get the requested data.
- The other functionality should remain the same as in the previous stage.

### Examples

**Example 1.** *GET request to the /aggregate?account=02248 endpoint:*

*Response code:* `429 TOO MANY REQUESTS`

**Example 2.** *GET request to the /aggregate?account=02248 endpoint:*

*Response code:* `503 SERVICE UNAVAILABLE`

**Example 3.** *GET request to the /aggregate?account=02248 endpoint:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id":"31969aef-ffbe-413a-8a94-bc920556a0d4",
    "serverId":"server-04",
    "account":"02248",
    "amount":"5120",
    "timestamp":"2023-12-24T00:02:31.886783206"
  },
  {
    "id":"dcc57df0-d815-497f-be1d-b3fb419b9bee",
    "serverId":"server-04",
    "account":"02248",
    "amount":"4933",
    "timestamp":"2023-12-21T10:33:56.886823126"
  },
  {
    "id":"398c135b-b055-4415-a7be-4beb4a3c7da8",
    "serverId":"server-25",
    "account":"02248",
    "amount":"1205",
    "timestamp":"2023-12-19T16:56:48.886729416"
  }
]
```

### *My Comments*

The instructions are vague/misleading as to how you should handle the case where a data server responds to all 5 attempts with an error code. To pass Hyperskill's tests, your program should treat this situation as if the server responded with an empty list.

You may assume a data server always responds with `200 OK`, `429 TOO MANY REQUESTS` or `503 SERVICE UNAVAILABLE`.


## Stage 4/4: Long queries

### Description

The transaction aggregator service now can handle remote service errors but another problem has recently arisen. The remote services tend to take quite long to respond. To improve performance of the aggregator, this problem should be handled too.

The main feature of the remote data services is that they send information about transactions made in a previous period. This means there will no new data for any account. This also means that if the aggregator has received transactions data for a given account, this information can be safely cached because it will be the same for any subsequent request.

Taking this into account, choose and implement a caching strategy for the transaction aggregator service. Using an in-memory cache will be enough for this project.

A good choice is using the cache functionality provided by Spring Boot.

Another way to improve performance is to make requests asynchronously so that waiting for a response does not block the thread and both responses can be received effectively at the same time.

### Objectives

- Implement caching in the transaction aggregator service to optimize its performance despite the performance of remote services may downgrade.
- Implement asynchronous requests to the remote services so that one request won't block the another one.

### *My Comments*

I completed all four stages using [`RestClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestClient.html), but there's a strong case for switching to [`WebClient`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html) for Stages 3 and 4. `RestClient` and `WebClient` have similar APIs, but the latter performs non-blocking (asynchronous) requests. Moreover, `WebClient` includes a `retry()` method that simplifies the solution to Stage 3. (More precisely, `retry()` methods are provided by the [`Mono<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Mono.html) and [`Flux<T>`](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html) classes used as return types by the relevant `WebClient` methods.)
