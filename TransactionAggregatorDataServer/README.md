# Data Server for Transaction Aggregator

## Project description

The **Transaction Aggregator** project does not operate by itself: it expects to communicate with servers at `http://localhost:8888` and `http://localhost:8889` that respond to requests at the `/transactions` endpoint with a JSON list of transactions for a given account (provided as a request parameter).

This project provides a simple implementation of such a data server for demonstration and testing of Transaction Aggregator solutions. To help with the analysis of aggregator performance, the server performs some basic logging during transaction requests.


## Configuration properties

The runtime behaviour of the data server can be adjusted through several settings. All of these have default values set in the `application.properties` file, but the defaults can be overridden by passing command-line arguments when starting the application (see next section). 

- `dataservice.id` (values: `1` or `2`; default: `1`). Servers 1 and 2 run on ports 8889 and 8888, respectively. Each has its own fixed list of transactions with entries for two accounts, 01055 and 02248.
- `dataservice.random` (values: `true` or `false`; default: `true`). This option tests the Transaction Aggregator functionality added in Stage 3. When set to `false`, the server always responds to calls at the `/transactions` endpoint with status `200 OK` and a list of transactions. When set to `true`, the server will randomly respond with status `200 OK`, `429 TOO MANY REQUESTS` or `503 SERVICE UNAVAILABLE`.
- `dataservice.delay` (values: any integer; default: `1000`). This property helps to make the caching and non-blocking functionality added in Stage 4 more apparent. When set to a positive value *n*, the server will sleep for *n* milliseconds before sending a response. With a reasonably high value of *n*, the initial response from the Transaction Aggregator for a given account will have a noticeable delay, which (if the aggregator is making asynchronous requests) should be approximately equal to the *maximum* (not the *sum*) of the delays from Servers 1 and 2. Subsequent (i.e., cached) calls for the same account should experience nearly instant replies, because the aggregator no longer needs to call the data servers before sending its response.


## Starting the server

Just for variety, I decided to use Maven for this project. The Spring Boot Maven Plugin lets you pass command-line arguments to the application using `-Dspring-boot.run.arguments`:
```sh
# Launch Server 1 on port 8889 with random responses and 1-second delay
$ mvn spring-boot:run

# Launch Server 2 on port 8888 with random responses and no delay
$ mvn spring-boot:run -Dspring-boot.run.arguments="--dataservice.id=2 --dataservice.delay=0"

# Launch Server 1 on port 8889 with a 3-second delay and without randomized responses
$ mvn spring-boot:run -Dspring-boot.run.arguments="--dataservice.random=false --dataservice.delay=3000"
```
