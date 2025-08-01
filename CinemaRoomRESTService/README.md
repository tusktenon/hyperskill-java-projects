# Cinema Room REST Service with Java

## Project description

You will practice concepts frequently tested in technical interviews at top tech companies. Always wanted to have your private movie theater and screen only the movies you like? You can buy a fancy projector and set it up in a garage, but how can you sell tickets? The idea of a ticket booth is old-fashioned, so let's create a special service for that! Make good use of Spring and write a REST service that can show the available seats, sell and refund tickets, and display the statistics of your venue. Pass me the popcorn, please!

[View more](https://hyperskill.org/projects/189)


## Stage 1/4: The show begins

### Description

There are many fun activities on the planet Earth, and one of them is going to the movies. It is arguably the most accessible, inclusive, and fulfilling entertainment. Bring your friends or loved ones — each movie is a whole new adventure waiting to be experienced.

Let's make our virtual movie theater with the help of a REST service. Our movie theater has 9 rows with 9 seats each. In this stage, you need to create a simple endpoint that will return the information about the cinema in JSON format.

### Objectives

Implement the `/seats` endpoint that handles `GET` requests and returns the information about the movie theatre.

The response should contain information about the rows, columns, and available seats. The response is a JSON object and has the following format:
```json
{
   "rows": 5,
   "columns": 6,
   "seats": [
      {
         "row": 1,
         "column": 1
      },

      ........

      {
         "row": 5,
         "column": 5
      },
      {
         "row": 5,
         "column": 6
      }
   ]
}
```

Our cinema room has 9 rows with 9 seats each, so the total number of respective rows and columns also amounts to 9 each.

Note that the `seats` array contains 81 elements, as there are 81 seats in the room.

### Example

*A `GET /seats` request.*
*Response body:*
```json
{
   "rows": 9,
   "columns": 9,
   "seats": [
      {
         "row": 1,
         "column": 1
      },
      {
         "row":1,
         "column":2
      },
      {
         "row": 1,
         "column": 3
      },

      ........

      {
         "row": 9,
         "column": 8
      },
      {
         "row": 9,
         "column": 9
      }
   ]
}
```


## Stage 2/4: Take your seat

### Description

Movie-goers should be able to check the availability of seats before purchasing a ticket. In this stage, you need to add an endpoint to check and purchase an available ticket. If the ticket has been purchased or the request contains wrong information about the ticket, return an error message.

### Objectives

Implement the `/purchase` endpoint that handles `POST` requests and marks a booked ticket as purchased.

A request should contain the following data:

- `row` — the row number;
- `column` — the column number.

Take these variables and check if the specified ticket is available. If the ticket is booked, mark the seat as purchased and don't show it in the list.

If the purchase is successful, the response body should be as follows:
```json
{
    "row": 5,
    "column": 7,
    "price": 8
}
```

To learn more about how you can create responses, you can take a look at the following topic: [Response bodies](https://hyperskill.org/learn/step/36523).

The ticket price is determined by a row number. If the row number is less or equal to 4, set the price at **10**. All other rows cost **8** per seat.

If the seat is taken, respond with a `400 (Bad Request)` status code. The response body should contain the following:
```json
{
    "error": "The ticket has been already purchased!"
}
```

If users pass a wrong row/column number, respond with a `400` status code and the following line:
```json
{
    "error": "The number of a row or a column is out of bounds!"
}
```

Show the ticket price when the `/seats` endpoint is accessed. See the first example for more details.

### Examples

**Example 1:** *a `GET /seats` request*

*Response body:*
```json
{
   "rows": 9,
   "columns": 9,
   "seats": [
      {
         "row": 1,
         "column": 1,
         "price": 10
      },
      {
         "row": 1,
         "column": 2,
         "price": 10
      },
      {
         "row": 1,
         "column": 3,
         "price": 10
      },

      ........

      {
         "row": 9,
         "column": 8,
         "price": 8
      },
      {
         "row": 9,
         "column": 9,
         "price": 8
      }
   ]
}
```

**Example 2:** *a `POST /purchase` correct request*

*Request body:*
```json
{
    "row": 3,
    "column": 4
}
```

*Response body:*
```json
{
    "row": 3,
    "column": 4,
    "price": 10
}
```

**Example 3:** *a `POST /purchase` request, the ticket is already booked*

*Request body:*
```json
{
    "row": 3,
    "column": 4
}
```

*Response body:*
```json
{
    "error": "The ticket has been already purchased!"
}
```

**Example 4:** *a `POST /purchase` request, a wrong row number*

*Request body:*
```json
{
    "row": 15,
    "column": 4
}
```

*Response body:*
```json
{
    "error": "The number of a row or a column is out of bounds!"
}
```


## Stage 3/4: A change of plans

### Description

We live in a fast world, and our plans may change very quickly. Let's add the ability to refund a ticket if a customer can't come and watch a movie. We will use tokens to secure the ticket refund process.

### Objectives

Change the JSON response when a customer purchases a ticket by making a `POST` request to the `/purchase` endpoint. Turn it into the following format:
```json
{
    "token": "00ae15f2-1ab6-4a02-a01f-07810b42c0ee",
    "ticket": {
        "row": 1,
        "column": 1,
        "price": 10
    }
}
```

We recommend using the `randomUUID()` method of the `UUID` class to generate tokens. Take a look at this [UUID Guide](https://www.baeldung.com/java-uuid) by Baeldung if you're interested in more detail.

Implement the `/return` endpoint, which will handle `POST` requests and allow customers to refund their tickets.

The request should have the `token` feature that identifies the ticket in the request body. Once you have the token, you need to identify the ticket it relates to and mark it as available. The response body should be as follows:
```json
{
    "ticket": {
        "row": 1,
        "column": 1,
        "price": 10
    }
}
```

The `ticket` should contain the information about the returned ticket.

If you cannot identify the ticket by the token, make your program respond with a `400` status code and the following response body:
```json
{
    "error": "Wrong token!"
}
```

### Examples

**Example 1:** *a correct `POST /purchase` request*

*Request body:*
```json
{
    "row": 3,
    "column": 4
}
```

*Response body:*
```json
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556",
    "ticket": {
        "row": 3,
        "column": 4,
        "price": 10
    }
}
```

**Example 2:** *`POST /return` with the correct token*

*Request body:*
```json
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```

*Response body:*
```json
{
    "ticket": {
        "row": 1,
        "column": 2,
        "price": 10
    }
}
```

**Example 3:** *`POST /return` with an expired token*

*Request body:*
```json
{
    "token": "e739267a-7031-4eed-a49c-65d8ac11f556"
}
```

Response body:
```json
{
    "error": "Wrong token!"
}
```


## Stage 4/4: The statistics

### Description

Your REST service knows how to show available tickets, sell them, and make a refund. Let's add statistics available only to the theatre managers.

### Objectives

Implement the `/stats` endpoint that will handle `GET` requests with URL parameters. If the URL parameters contain a `password` key with a `super_secret` value, return the movie theatre statistics in the following format:
```json
{
    "income": 0,
    "available": 81,
    "purchased": 0
}
```

Take a look at the description of keys:

- `income` — shows the total income of sold tickets.
- `available` — shows how many seats are available.
- `purchased` — shows how many tickets were purchased.

If the parameters don't contain a password key or a wrong value has been passed, respond with a `401` status code. The response body should contain the following:
```json
{
    "error": "The password is wrong!"
}
```

### Examples

**Example 1:** *a `GET /stats` request with no parameters*

*Response body:*

```json
{
    "error": "The password is wrong!"
}
```

**Example 2:** *a `GET /stats` request with the correct password*

*Response body:*
```json
{
    "income": 30,
    "available": 78,
    "purchased": 3
}
```
