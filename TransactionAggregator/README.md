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

Your service should accept a request with an account number and fetch transaction data from both servers for that account. After that, you should combine the data into a single list, sort them by the timestamp so what the newer transactions come first, and return the resulting list to the client.

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
