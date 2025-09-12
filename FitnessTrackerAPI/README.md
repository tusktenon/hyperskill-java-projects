# Fitness Tracker API

## Project Description

You probably have used public APIs in your projects but have you ever wanted to build such an API on your own? If yes, this project is the right place to start. You will create a public API that allows client applications to upload and download data from fitness devices, protect your API with API-Key authentication and apply different rate limit policies to different categories of clients.

- [View more](https://hyperskill.org/projects/408) (official project page)
- [My notes](./NOTES.md) on this project


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


## Stage 2/5: Developer registration

### Description

The first version of the API is ready, so it's time to consider the client applications. Since we're creating a public API, we won't make any client applications ourselves. Instead, we'll allow all developers to make whatever applications they like, such as web, mobile, desktop, IoT or any other kind, that can utilize our API.

We also aim to monitor these applications, for instance, to set different service levels for free and premium clients in future. Here's the plan: we let developers sign up, register their applications with our service, and then we'll implement an authentication process to ensure the `/api/tracker` endpoints are accessible to registered applications only.

Now, let's begin with the developer sign-up process.

To activate Spring Security in the project, add the `spring-boot-starter-security` starter to the dependencies section of the `build.gradle` file.

Create two new endpoints: `POST /api/developers/signup` for new developer registration and `GET /api/developers/<id>` for accessing the profile of the appropriate developer. Use basic **HTTP authentication** for this endpoint.

The `POST /api/developers/signup` endpoint should accept a JSON request body with two fields, `email` and `password`. As registration data is vital, you need to validate the input to ensure both fields meet the requirements. If the email isn't null, matches the email pattern and is unique, and if the password isn't null and not blank, the service should return the status code `201 CREATED` and the response header `Location` containing the URL of the newly created developer. If either field isn't valid, including if a developer with the submitted email address is already registered, the service should return the status code `400 BAD REQUEST`. You might find the `spring-boot-starter-validation` starter useful for this purpose.

The `GET /api/developers/<id>` endpoint should be accessible only to the authenticated developer with the matching id. If access is granted, the endpoint should return the status code `200 OK` and a response body in JSON format, containing the id and the email of the corresponding developer. If an unauthenticated developer tries to access this endpoint, it should respond with the status code `401 UNAUTHORIZED`. If an authenticated client tries to access the endpoint but their id doesn't match the id in the request path, the service should respond with the status code `403 FORBIDDEN`.

At this stage, don't limit access to the `/api/tracker` endpoints, they should be accessible by all.

Also, when setting up access rules for the API, ensure the `/actuator/shutdown` endpoint remains accessible, or else some tests may not pass. As we're building a REST API, don't use sessions. Plus, if you're using tools like Postman and H2 console, remember to add the necessary methods to the security filter chain configuration:
```java
// other methods
.csrf(csrf -> csrf.disable()) // For Postman
.headers(headers -> headers.frameOptions().disable()) // For the H2 console
.authorizeHttpRequests(auth -> auth
        .requestMatchers("/actuator/shutdown").permitAll() // for tests
        .requestMatchers("/error").permitAll() // to prevent access errors for validation exceptions
        // other matchers
)
.sessionManagement(sessions -> sessions
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
)
// other methods
```

### Objectives

- Activate Spring Security in the project.

- Add the `POST /api/developers/signup` endpoint that should be accessible to all users. The endpoint should accept a JSON response body with an email address and a password for a new developer. The email must be unique, not null and match the email pattern. The password must be not null and not blank:
    ```text
    {
      "email": <string, unique, not null, matches the email pattern>,
      "password": <string, not null, not blank>
    }
    ```
If the request body is valid, the endpoint should respond with the status code `201 CREATED` and the response header `Location` showing the URL of the created developer:
    ```text
    Location: /api/developers/<id>
    ```
If any of the request body fields fail to meet the requirements, the endpoint should respond with the status code `400 BAD REQUEST`.

- Add the `GET /api/developers/<id>` endpoint to return the profile of the corresponding developer as a JSON object:
    ```text
    {
      "id": <id>,
      "email": <string>
    }
    ```
This endpoint should be accessible only by the authenticated developer with the same id as in the request path. In case the request is made by an unauthenticated client, the endpoint should respond with the status code `401 UNAUTHORIZED`. If the request is made by an authenticated developer but their id doesn't match the id in the request path, the endpoint should respond with the status code `403 FORBIDDEN`.

- Save all registered developer data in the database.

- The `/api/tracker` endpoints should be open to all.

### Examples

**Example 1.** *POST request to the /api/developers/signup endpoint:*

*Request body:*
```json
{
  "email": "johndoe@gmail.com",
  "password": "qwerty"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /api/developers/9062
```

**Example 2.** *POST request to the /api/developers/signup endpoint lacking a password:*

*Request body:*
```json
{
  "email": "johndoe@gmail.com"
}
```

*Response code:* `400 BAD REQUEST`

**Example 3.** *POST request to the /api/developers/signup endpoint with an email already registered:*

*Request body:*
```json
{
  "email": "johndoe@gmail.com",
  "password": "qwerty"
}
```

*Response code:* `400 BAD REQUEST`

**Example 4.** *GET request to the /api/developers/9062 with login=johndoe@gmail.com and password=qwerty:*

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": 9062,
  "email": "johndoe@gmail.com"
}
```

**Example 5.** *GET request to the /api/developers/0165 with login=johndoe@gmail.com and password=qwerty:*

*Response code:* `403 FORBIDDEN`


## Stage 3/5: Application registration

### Description

Now that we've completed the developer registration flow, let's allow authenticated developers to sign their applications up with our service.

To sign an application up, a developer needs to specify its name and description. In return, our service assigns a unique API key to each application which will later be used to authenticate them.

This API key uniquely identifies the application it's assigned to. As the API key serves as an authentication mechanism, it should be tough to guess or predict. One way to approach this is generating a random string, sufficiently long. For instance, you can generate a random byte array and convert it into a hex string. If the key length is considerable, the likelihood of API keys clashing is very low.

You also need to make changes to the `GET /api/developers/<id>` endpoint. Now, it should include an array of all applications listed according to the order they were registered in, with newer applications showing up first.

### Objectives

- Add the `POST /api/applications/register` endpoint. This will accept the following request body in JSON format:
    ```text
    {
      "name": <string, not null, not blank, unique>,
      "description": <string, not null>
    }
    ```
`name` is the application's name and should be a unique non-blank string; `description` is the application's description and can be empty or blank. If registration is successful, the endpoint should return a status code `201 CREATED`, plus the response body below:
    ```text
    {
      "name": <string>,
      "apikey": <string, not null, not blank, unique>
    }
    ```
The response should comprise the application's name and the assigned unique API key. If the request body contains an invalid field, the endpoint should respond with a status code `400 BAD REQUEST`. If an unauthenticated developer sends the request, the endpoint should send a response with the status code `401 UNAUTHORIZED`.

- Update the `GET /api/developers/<id>` endpoint. It should now include a JSON array of all applications of the requested developer, covering each application's id, name, description, and API key. Applications in the array should be arranged according to when they were registered, with newer applications listed first:
    ```text
    {
      "id": <id>,
      "email": <string>,
      "applications": [
        {
          "id": <id>,
          "name": <string>,
          "description": <string>,
          "apikey": <string>
        },
        {
          "id": <id>,
          "name": <string>,
          "description": <string>,
          "apikey": <string>
        },
        ...
      ]
    }
    ```

- The access rules from the previous stage still apply.

### Examples

**Example 1.** *POST request to the /api/developers/signup endpoint:*

*Request body:*
```json
{
  "email": "johndoe@gmail.com",
  "password": "qwerty"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /api/developers/9062
```

**Example 2.** *POST request to the /api/applications/register endpoint with login=johndoe@gmail.com and password=qwerty:*

*Request body:*
```json
{
  "name": "Fitness App",
  "description": "demo application"
}
```

*Response code:* `201 CREATED`

*Response body:*
```json
{
  "name": "Fitness App",
  "apikey": "21da3cc8020517ecaf2e0781b9f679c56fe0f119"
}
```

**Example 3.** *POST request to the /api/applications/register endpoint with login=johndoe@gmail.com and password=qwerty:*

*Request body:*
```json
{
  "name": "Fitness App",
  "description": "demo application"
}
```

*Response code:* `400 BAD REQUEST`

**Example 4.** *POST request to the /api/applications/register endpoint with login=johndoe@gmail.com and password=qwerty:*

*Request body:*
```json
{
  "name": "",
  "description": "demo application"
}
```

*Response code:* `400 BAD REQUEST`

**Example 5.** *GET request to the /api/developers/9062 endpoint with login=johndoe@gmail.com and password=qwerty:*

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": 9062,
  "email": "johndoe@gmail.com",
  "applications": [
    {
      "id": 4624,
      "name": "Fitness App",
      "description": "demo application",
      "apikey": "21da3cc8020517ecaf2e0781b9f679c56fe0f119"
    }
  ]
}
```


## Stage 4/5: API-key authentication

### Description

Once everything is set up, developers can register their applications and receive API keys. With these keys, you can finally protect your tracker API using API key authentication.

The authentication process goes like this:

When an application sends a request to the `POST /api/tracker` or `GET /api/tracker` endpoint, it needs to include the `X-API-Key` header in the request. This header should contain the application's unique API key. If the supplied API key is valid, the application becomes authenticated. However, if the necessary header is missing or fails to contain a valid API key, the authentication gets rejected, and the `401 UNAUTHENTICATED` status code is returned.

You might want to build a custom security filter and add it to the existing security filter chain to secure this API protection method.

Besides, you'll want to track which application is uploading data to your service. This can be achieved by storing the name of the publisher application in each fitness data record.

The basic HTTP authentication for developers should continue to function as before. Any unauthenticated access to the `POST /api/tracker` and `GET /api/tracker` endpoints should be blocked.

### Objectives

- Enable API key authentication for the `POST /api/tracker` or `GET /api/tracker` endpoints.

- Now, the `POST /api/tracker` endpoint should be available only to authenticated applications. An application becomes authenticated by providing a valid API key in the `X-API-Key` header. In addition to the fields provided in the request body, include the publisher application name in each record.

- Similarly, the `GET /api/tracker` endpoint should only be available to authenticated applications. An application becomes authenticated by providing a valid API key in the `X-API-Key` header. Fitness data records now need to contain the names of the applications that published them:
    ```text
    [
      {
        "id": <id>,
        "username": <string>,
        "activity": <string>,
        "duration": <integer>,
        "calories": <integer>,
        "application": <string>
      },
      {
        "id": <id>,
        "username": <string>,
        "activity": <string>,
        "duration": <integer>,
        "calories": <integer>,
        "application": <string>
      },
      ...
    ]
    ```

- Continue to support basic HTTP authentication for the `GET /api/developers/<id>` and `POST /api/applications/register` endpoints.

### Examples

**Example 1.** *POST request to the /api/developers/signup endpoint:*

*Request body:*
```json
{
  "email": "johndoe@gmail.com",
  "password": "qwerty"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /api/developers/9062
```

**Example 2.** *POST request to the /api/applications/register endpoint with login=johndoe@gmail.com and password=qwerty:*

*Request body:*
```json
{
  "name": "Fitness App",
  "description": "demo application"
}
```

*Response code:* `201 CREATED`

*Response body:*
```json
{
  "name": "Fitness App",
  "apikey": "21da3cc8020517ecaf2e0781b9f679c56fe0f119"
}
```

**Example 3.** *POST request to the /api/tracker endpoint with X-API-Key=21da3cc8020517ecaf2e0781b9f679c56fe0f119:*

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

**Example 4.** *GET request to the /api/tracker endpoint with X-API-Key=21da3cc8020517ecaf2e0781b9f679c56fe0f119:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "username": "user-12",
    "activity": "swimming",
    "duration": 950,
    "calories": 320,
    "application": "Fitness App"
  }
]
```

**Example 5.** *GET request to the /api/tracker endpoint with X-API-Key=abc:*

*Response code:* `401 UNAUTHENTICATED`
