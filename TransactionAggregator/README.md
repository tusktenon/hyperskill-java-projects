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
