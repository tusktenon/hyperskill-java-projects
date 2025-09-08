# Fitness Tracker API

## Project Description

You probably have used public APIs in your projects but have you ever wanted to build such an API on your own? If yes, this project is the right place to start. You will create a public API that allows client applications to upload and download data from fitness devices, protect your API with API-Key authentication and apply different rate limit policies to different categories of clients.

[View more](https://hyperskill.org/projects/408)


## Stage 1/5: Basic API

### Description

This project's heart is the API that enables the upload and download of fitness tracker data. For this aim, you need to establish two endpoints - `POST /api/tracker` and `GET /api/tracker`.

`POST /api/tracker` should be made to accept data in JSON format. The request body must carry the username of the data uploader, the name of a fitness activity, the activity's duration in seconds, and the number of calories burned. You can trust clients to transmit only accurate data with all fields filled and sensible values included.

The `GET /api/tracker` has to retrieve all available fitness data as a JSON array, which should be sorted by upload order, displaying the most recent records first.

This will lay a solid groundwork for future API expansion.

All data must be stored in the H2 database on disk. Ensure to add these lines to the `application.properties` file:
```text
spring.datasource.url=jdbc:h2:file:../fitness_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false
```

### Objectives

- Construct the `POST /api/tracker` endpoint to accept fitness data in the following JSON format:
    ```text
    {
      "username": <string>,
      "activity": <string>,
      "duration": <integer>,
      "calories": <integer>
    }
    ```
and respond with `201 CREATED` as the status code. Clients are bound to send accurate data only.

- Build the `GET /api/tracker` endpoint that responds with a `200 OK` status code and a response body that carries a JSON array. The array is to contain all existing fitness data, sorted by their upload order, positioned from newest to oldest:
    ```text
    [
      {
        "id": <id>,
        "username": <string>,
        "activity": <string>,
        "duration": <integer>,
        "calories": <integer>
      },
      {
        "id": <id>,
        "username": <string>,
        "activity": <string>,
        "duration": <integer>,
        "calories": <integer>
      },
      ...
    ]
    ```

### Examples

**Example 1.** *POST request to the /api/tracker endpoint:*

*Request body:*
```json
{
  "username": "user-12",
  "activity": "swimming",
  "duration": 950,
  "calories": 320
}
```

*Response code:* `201 CREATED`

**Example 2.** *Another POST request to the /api/tracker endpoint:*

*Request body:*
```json
{
  "username": "user-12",
  "activity": "hiking",
  "duration": 4800,
  "calories": 740
}
```

Response code: `201 CREATED`

**Example 3.** *GET request to the /api/tracker endpoint:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "username": "user-12",
    "activity": "hiking",
    "duration": 4800,
    "calories": 740
  },
  {
    "username": "user-12",
    "activity": "swimming",
    "duration": 950,
    "calories": 320
  }
]
```
