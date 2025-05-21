# Web Calendar (Java)

## Project Description

These days, our lives are hectic and eventful. It's tough to keep track of everything that happens around us. Let's create a web calendar to store and manage all upcoming events. Use the Spring Boot framework and create a REST API to save and manage events. The Spring Boot framework allows you to start a web application with a minimal setup.

[View more](https://hyperskill.org/projects/396)


## Stage 1/4: What should I do today?

### Description

Nowadays, our lives are filled with many events and tasks, making it difficult to keep track of everything. To solve this problem, we can create a web calendar to store and manage all upcoming events. To achieve this, we can use the Spring Boot framework and create a REST API to save and manage events. The Spring Boot framework is perfect for building web applications with minimal setup requirements.

### Theory

Create a Spring Boot application object to start working with it. First, create a Spring project using Spring Boot. The following dependencies are already included in our study project in *build.gradle* file:

1. `spring-boot-starter` — the core starter with the autoconfiguration support, logging, and YAML;
2. `spring-boot-starter-web` — the starter for building the web, including RESTful, applications using Spring MVC. It uses Tomcat as the default embedded container.

### Objectives

Create a resource that handles `GET` requests for the `/event/today` endpoint and sends the following JSON object as a response. The endpoint should return the response status code `200`. Run a Spring Boot application on the `28852` port. It is already set in the *application.properties* of the project.

### Example

**Example 1:** *GET request for the /event/today endpoint*

*Response:* `200 OK`

*Response body:*
```json
[]
```


## Stage 2/4: GET vs POST

### Description

Let's add the ability to handle `POST` requests from the user side and parse their arguments. To parse the arguments from the `POST` request, use the `@RequestBody` annotation. This annotation indicates a method parameter should be bound to the body of the web request. The body of the request is passed through an `HttpMessageConverter` to resolve the method argument depending on the content type of the request.

### Objectives

Create a new resource to handle `POST` requests for the `/event` endpoint. It must require the following arguments in the request body:

- An `event` argument of the `String` type.
- A `date` argument of the `LocalDate` type. The correct format is `YYYY-MM-DD`.

If a user sends the correct response, display the following message: `The event has been added!`, and show the user data:
```json
{
    "message": "The event has been added!",
    "event": "Client event name",
    "date": "Client date"
}
```

### Examples

**Example 1:** *Wrong `POST` request for the /event endpoint*

*Request body:*
```json
{
    "date": "2021-02-10"
}
```

*Response:* `400 Bad Request`

*No Response body.*

**Example 2:** *Wrong `POST` request for the /event endpoint*

*Request body:*
```json
{
    "event": "Video conference"
}
```

*Response:* `400 Bad Request`

*No Response body.*

**Example 3:** *Сorrect `POST` request for the /event endpoint*

*Request body:*
```json
{
    "event": "Video conference",
    "date": "2020-11-15"
}
```

*Response:* 200 OK

*Response body:*
```json
{
    "message": "The event has been added!",
    "event": "Video conference",
    "date": "2020-11-15"
}
```
