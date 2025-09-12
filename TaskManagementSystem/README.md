# Task Management System

## Project Description

Have you ever used any task management systems like Jira or Trello? This project is the perfect starting point to build a small yet feature rich application on your own. In this project, you will develop a task management system that allows users to create, update, and manage tasks. You'll implement JWT authentication to protect your system and create aggregate views of tasks. This will give you a good practice in creating advance CRUD applications and provide you with the necessary skills to create a useful web service for your portfolio and daily use.

- [View more](https://hyperskill.org/projects/423) (official project page)
- [My notes](./NOTES.md) on this project

## Stage 1/5: Registering users

### Description

In a task management system, you'll often be working with multiple users. Therefore, it's useful to begin by setting up the user registration process and API access. It's important you store user data in a database right from the start and set up the project accordingly.

You should provide a REST API for users. Now, you need to create two endpoints, one for user registration and another one for testing access control. Using Spring Security, you can secure these endpoints. To carry out tests, you should set up Spring Security correctly:
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .httpBasic(Customizer.withDefaults()) // enable basic HTTP authentication
            .authorizeHttpRequests(auth -> auth
                    // other matchers
                    .requestMatchers("/error").permitAll() // expose the /error endpoint
                    .requestMatchers("/actuator/shutdown").permitAll() // required for tests
                    .requestMatchers("/h2-console/**").permitAll() // expose H2 console
            )
            .csrf(AbstractHttpConfigurer::disable) // allow modifying requests from tests
            .sessionManagement(sessions ->
                    sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
            )
            .build();
    }
```

Store all data in the H2 database on disk. Don't forget to include these lines in the `application.properties` file:
```text
spring.datasource.url=jdbc:h2:file:../tms_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa

spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.settings.trace=false
spring.h2.console.settings.web-allow-others=false
```

### Objectives

- Set up a `POST /api/accounts` endpoint that takes a unique email and password as a JSON object in this format:
    ```text
    {
        "email": <unique string>,
        "password": <string>
    }
    ```
and responds with a `200 OK` status code. Check the request body. If the `email` or `password` are empty, blank, or missing, the endpoint should respond with a `400 BAD REQUEST` status code. The endpoint should also respond with a `400 BAD REQUEST` status code if the email format is invalid. Additionally, ensure that the password is at least 6 characters long. If the password is shorter than this, the endpoint should respond with a `400 BAD REQUEST` status code.

- Guarantee each user has a unique email address. If a registration request includes an email address already in the system, the user registration endpoint should respond with a `409 CONFLICT` status code. Treat email addresses as case-insensitive, so `address@domain.net` and `ADDRESS@DOMAIN.NET` are regarded as the same address.

- Create a `GET /api/tasks` endpoint that responds with a `200 OK` status code.

- Enable security and require users to authenticate using basic HTTP authentication to access the `GET /api/tasks` endpoint. If a user tries to access it without providing a valid username and password, the service should respond with a `401 UNAUTHORIZED` status code.

- Ensure that all data remains even after the server restarts.

### Examples

**Example 1.** *POST request to `/api/accounts` endpoint:*

*Request body:*
```json
{
  "email": "address@domain.net",
  "password": "password"
}
```
*Response code:* `200 OK`

**Example 2.** *POST request to `/api/accounts` endpoint with an invalid request body:*

*Request body:*
```json
{
  "email": "address",
  "password": ""
}
```
*Response code:* `400 BAD REQUEST`

**Example 3.** *POST request to `/api/accounts` endpoint with an email address that's already taken:*

*Request body:*
```json
{
  "email": "ADDRESS@DOMAIN.NET",
  "password": "password"
}
```
*Response code:* `409 CONFLICT`

**Example 4.** *GET request to `/api/tasks` endpoint with correct credentials:*

*Response code:* `200 OK`

**Example 5.** *GET request to `/api/tasks` endpoint with incorrect credentials:*

*Response code:* `401 UNAUTHORIZED`


## Stage 2/5: Creating tasks

### Description

In the last stage, you enabled users to register on the Task Management System. They should now be able to create tasks and view those created by others. Additionally, you need to implement a function that allows filtering tasks by their author for user convenience.

A task should be a straightforward object encompassing a title, which should never be blank or empty, a description explaining the task's essence, and a status indicating its current state. The task should also hold information about the user who created it and who will own the task.

Any authenticated user should have the ability to create a task and view a list of all tasks in the system. To make it user-friendly, arrange the task list so that the most recent tasks show up first.

### Objectives

- Create the `POST /api/tasks` endpoint to accept a JSON request body with new task details:
    ```text
    {
      "title": <string, not null, not blank>,
      "description": <string, not null, not blank>
    }
    ```
If the request body is valid, the endpoint should respond with `200 OK` status code and a JSON response body:
    ```text
    {
      "id": <string>,
      "title": <string>,
      "description": <string>,
      "status": "CREATED",
      "author": <string>
    }
    ```
The `id` field is a string representation of the task's unique identifier. This doesn't mean that the identifier must be a string, but the response body should present it as a string value to not depend on the database implementation. The `author` field should feature the author email in lowercase. If the request body is not valid, the endpoint should return a `400 BAD REQUEST` status code.

- Modify the `GET /api/tasks` endpoint to respond with `200 OK` status code and return a JSON array of all created tasks, or an empty array if no tasks exist. Each array's element should follow the same format as mentioned above:
    ```text
    [
      {
        "id": <string>,
        "title": <string>,
        "description": <string>,
        "status": "CREATED",
        "author": <string>
      },
      // other tasks
    ]
    ```
The array should display newer tasks first.

- The `GET /api/tasks` endpoint should also accept an optional `author` parameter to return an array of tasks authored by a particular user. Treat this parameter as case insensitive:
    ```text
    GET /api/tasks?author=address@domain.net
    ```

- Only authenticated users should have access to the tasks endpoints. If accessed by unauthorized users, respond with a `401 UNAUTHORIZED` status code.

- Store all data in the database.

### Examples

**Example 1.** *POST request to `/api/tasks` endpoint by a registered user with valid credentials (`username=user1@mail.com`):*

*Request body:*
```json
{
  "title": "new task",
  "description": "a task for anyone"
}
```
*Response code:* `200 OK`

*Response body:*
```json
{
  "id": "1",
  "title": "new task",
  "description": "a task for anyone",
  "status": "CREATED",
  "author": "user1@mail.com"
}
```

**Example 2.** *POST request to `/api/tasks` endpoint with an invalid request body by an authenticated user:*

*Request body:*
```json
{
  "title": "",
  "description": "a task for anyone"
}
```
*Response code:* `400 BAD REQUEST`

**Example 3.** *POST request to `/api/developers/signup` endpoint with invalid credentials:*

*Request body:*
```json
{
  "title": "new task",
  "description": "a task for anyone"
}
```
*Response code:* `401 UNAUTHORIZED`

**Example 4.** *GET request to `/api/tasks` endpoint with valid user credentials:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "1",
    "title": "new task",
    "description": "a task for anyone",
    "status": "CREATED",
    "author": "user1@mail.com"
  }
]
```

**Example 5.** *GET request to `/api/tasks?author=USER1@mail.com` with valid user credentials:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "1",
    "title": "new task",
    "description": "a task for anyone",
    "status": "CREATED",
    "author": "user1@mail.com"
  }
]
```


## Stage 3/5: Authenticating with JWT

### Description

Great job so far! It's now time to enhance the sign-in process to improve user experience. Instead of making a user supply their login details for every request, you should let them give the details only once to receive an authentication token. They can then use this token for future sign ins.

This will make the user experience better without compromising security.

The normal way to put such token-based sign ins into action is by using JWT. However, the tests won't make you use the JWT format, and you're free to use any opaque token, as long as the service can recognize the user by that token.

To put the bearer token sign in to use, you need to create an endpoint for users to sign in and get their tokens. This endpoint should be available only to registered users using basic HTTP sign ins. Any other endpoint, apart from the `POST /api/accounts`, should be available if a request has the Authorization header with a functioning bearer token:
```text
Authorization: Bearer <token value>
```

The rest of the service should work as it did in the previous stage.

### Objectives

- Create the `POST /api/auth/token` endpoint that should be accessible using basic HTTP sign in. If the user signs in successfully, the endpoint should respond with the status code `200 OK` and a JSON response body:
    ```text
    {
      "token": <string>
    }
    ```
The `token` field contains a string representation of the access token provided to the user. The token should have a reasonable expiration time, allowing the user to utilize the token without frequently signing in again. If the user does not provide valid details, the endpoint should respond with a `401 UNAUTHORIZED` status code.

- Update the security settings so that any other secure endpoint can be accessed using the bearer token sign in with the granted access tokens.

- From now on, any requests to the API will be tested using access tokens that will be added to the `Authorization` header:
    ```text
    Authorization: Bearer <string token value>
    ```
But remember, in this project you don't need to turn off the basic HTTP sign in completely.

- All other endpoints should work the same way as they did in the previous stage.

### Examples

**Example 1**. *POST request to the `/api/auth/token` endpoint by a registered user with valid details (`username=user2@mail.com`):*

*Response code:* `200 OK`

*Response body:*
```json
{
  "token": "eyJraWQiOiJkM2E4YWY0NC0xNzc3LTRmOTAtYjc5Yy03NDRkMTI4MDQxNGQiLCJhbGciOiJSUzI1NiJ9
            .eyJzdWIiOiJ1c2VyMkBtYWlsLmNvbSIsImV4cCI6MTcwMzA4MTIyNiwiaWF0IjoxNzAzMDc3NjI2LCJ
            zY29wZSI6WyJST0xFX1VTRVIiXX0.g069OwkofwRPa1vuIU-Vc30UYXzmJihlg9KzxQ7LqJzKsJJO4_o
            sSVwq7eqniiYurIBToXiW_PttteuOOps6ryDZXKqg3FBoHEiRLoUgn9vNgRydFOBo1WwB_fHxOB0xFW2
            RrGbDlWpFs9F_8ap-O9BHf74VU4L1HRn6vTA7yhtqfBdAZscPY6XCVjUdPwXMQnNqJy2vOTdFNd1-V9X
            X5GEFbndXMyTsQAKfhTnjdn151unbzYnllUwtb4xtRfpCLr47KuVSGrOTfDbBbjx91SB2i0wfq46b5ty
            lCOMR7nsMWuhBxV8oqlIICLPokEejB8jAVcZXcsxSqQz9AHopGg"
}
```

**Example 2**. *POST request to the `/api/tasks` endpoint with the token from Example 1:*

*Request body:*
```json
{
  "title": "second task",
  "description": "another task"
}
```

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": "2",
  "title": "second task",
  "description": "another task",
  "status": "CREATED",
  "author": "user2@mail.com"
}
```

**Example 3**. *GET request to the `/api/tasks` endpoint with the token from Example 1:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "2",
    "title": "second task",
    "description": "another task",
    "status": "CREATED",
    "author": "user2@mail.com"
  },
  {
    "id": "1",
    "title": "new task",
    "description": "a task for anyone",
    "status": "CREATED",
    "author": "user1@mail.com"
  }
]
```

**Example 4**. *GET request to the `/api/tasks` with an expired access token:*

*Response code:* `401 UNAUTHORIZED`


## Stage 4/5: Assigning tasks

### Description

In this stage, you'll set up task management. Task creators should be able to assign their tasks to other users. Additionally, both task creators and assignees should have the option to update the task's status.

Besides listing all tasks and filtering them by creator, a client should also be capable of filtering tasks by assignee.

Let's delve into this functionality.

When a user makes a task, it gets the status `CREATED` and doesn't have an assignee. The creator can pick another user by their email and assign the task to them. Plus, the creator can remove the assignee from the task. Other users can still see the tasks made by others but they can't modify them.

Moreover, the creator can alter the task's status. The possible statuses, besides `CREATED`, are `IN_PROGRESS` and `COMPLETED`. If the task has an assignee, that assignee can also update the task status. No other user has the authorization to change the status of the task.

This is the beginning of actual task management. Remember, previous functionalities should remain operational.

### Objectives

- Refine the `POST /api/tasks` endpoint. It should take the same request body as before but its response body should now include the assignee field with the value `"none"` since the new task isn't assigned:
    ```text
    {
      "id": <string>,
      "title": <string>,
      "description": <string>,
      "status": "CREATED",
      "author": <string>,
      "assignee": "none"
    }
    ```

- Make the `PUT /api/tasks/<taskId>/assign` endpoint. It should accept the following JSON request body:
    ```text
    { 
      "assignee": <email address|"none"> 
    }
    ```
and respond with the status code `200 OK` and a response body mirroring the updated task state:
    ```text
    {
      "id": <string>,
      "title": <string>,
      "description": <string>,
      "status": <string>,
      "author": <string>,
      "assignee": <assignee's email>
    }
    ```
The `assignee` field should contain either the valid email address of a registered user or `"none"` if the author wants to remove the previous assignment. If the `taskId` path variable doesn't reflect the ID of a task, the endpoint should respond with the status code `404 NOT FOUND`. Likewise, if the assignee's email isn't associated with a registered user, the endpoint should reply with the status code `404 NOT FOUND`. If the `assignee` isn't a valid email address or `"none"`, return the status code `400 BAD REQUEST`. If the user who's trying to assign the task isn't its author, respond with the status code `403 FORBIDDEN`.

- Create the `PUT /api/tasks/<taskId>/status` endpoint that should accept the following JSON request body:
    ```text
    {
      "status": <"CREATED"|"IN_PROGRESS"|"COMPLETED">
    }
    ```
and reply with the status code `200 OK` and a response body that shows the updated task state:
    ```text
    {
      "id": <string>,
      "title": <string>,
      "description": <string>,
      "status": <new status>,
      "author": <string>,
      "assignee": <string>
    }
    ```
The `status` field should only have one of the permitted values, which are `CREATED`, `IN_PROGRESS`, or `COMPLETED`. If it's not one of these, return the response code `400 BAD REQUEST`. If the user trying to change the task status isn't the author or assignee, respond with the status code `403 FORBIDDEN`. If the `taskId` path variable doesn't refer to an existing task ID, the endpoint should respond with the status code `404 NOT FOUND`.

- Alter the `GET /api/tasks` endpoint. Now it should accept a new optional parameter, assignee, for filtering tasks. The assignee parameter holds a user's email address and should be treated case insensitive. A request can contain any, both or none of the parameters to filter tasks by author, assignee, or both, or just return a list of all tasks:
    ```text
    [
      {
        "id": <string>,
        "title": <string>,
        "description": <string>,
        "status": <string>,
        "author": <string>,
        "assignee": <string>
      },
      // other tasks
    ]
    ```

### Examples

**Example 1**. *PUT request to the `/api/tasks/1/assign` endpoint by the task's creator:*

*Request body:*
```json
{
  "assignee": "user2@mail.com"
}
```

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": "1",
  "title": "new task",
  "description": "a task for anyone",
  "status": "CREATED",
  "author": "user1@mail.com",
  "assignee": "user2@mail.com"
}
```

**Example 2**. *PUT request to the `/api/tasks/1/status` endpoint by the task's assignee:*

*Request body:*
```json
{
  "status": "COMPLETED"
}
```

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": "1",
  "title": "new task",
  "description": "a task for anyone",
  "status": "COMPLETED",
  "author": "user1@mail.com",
  "assignee": "user2@mail.com"
}
```

**Example 3**. *GET request to the `/api/tasks?assignee=user2@mail.com` endpoint by an authenticated user:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "1",
    "title": "new task",
    "description": "a task for anyone",
    "status": "COMPLETED",
    "author": "user1@mail.com",
    "assignee": "user2@mail.com"
  }
]
```

**Example 4**. *GET request to the `/api/tasks?author=user2@mail.com&assignee=user1@mail.com` by an authenticated user:*

*Response code:* `200 OK`

*Response body:*
```json
[]
```

**Example 5**. *GET request to the `/api/tasks?author=user2@mail.com&assignee=user1@mail.com` by an unauthenticated user:*

*Response code:* `401 UNAUTHORIZED`


## Stage 5/5: Leaving comments

### Description

In this stage, you need to create ways for users to interact. Any logged-in user should have the option to leave comments on any task. Moreover, they should be able to request all the comments on any given task. To make things easier for the users, comments should include the author's details and should be sorted from newest to oldest.

Furthermore, when a user asks for a task list, the details of each task should include the total comments it received. As you add this function, keep performance in mind and try to limit the number of database requests.

All functions from previous stages should remain functional.

After completing this stage, you will have a solid foundation of a task management system that you can further enhance by introducing features such as modifying and deleting tasks and comments, enabling file attachments, and more.

### Objectives

- Create the `POST /api/tasks/<taskId>/comments` endpoint to accept a JSON body with the comment text:
    ```text
    {
      "text": <string, not blank>
    }
    ```
and reply with the status code `200 OK`. If the request body is not valid, return the status code `400 BAD REQUEST`. If you cannot find any task using the supplied `taskId` path variable, answer with the status code `404 NOT FOUND`. Only logged-in users can post comments, otherwise, reply with the status code `401 UNAUTHORIZED`.

- Create the `GET /api/tasks/<taskId>/comments` endpoint to fetch all the comments for the task identified by an identifier. The endpoint should reply with the status code `200 OK` and a JSON array of comments containing the following details:
    ```text
    [
      {
        "id": <string>,
        "task_id": <string>,
        "text": <string>,
        "author": <string>
      },
      // other comments
    ]
    ```
where `id` is the unique identifier of the comment, `task_id` is the identifier of the task the comment pertains to, `text` is the comment's text and `author` is the email of the user who made the comment. The array should be sorted so that newer comments appear first.
If the user is not logged in, return `401 UNAUTHORIZED`. If no task can be found using the provided `taskId` path variable, answer with the status code `404 NOT FOUND`.

- Update the `GET /api/tasks` endpoint. Now the fetched list of tasks should include the total number of comments for each task:
    ```text
    [
      {
        "id": <string>,
        "title": <string>,
        "description": <string>,
        "status": <string>,
        "author": <string>,
        "assignee": <string>,
        "total_comments": <integer>
      },
      // other tasks
    ]
    ```
Other requirements for this endpoint must remain the same, including filtering.

### Examples

**Example 1.** *POST request to the `/api/tasks/1/comments` endpoint by a logged-in user:*

*Request body:*
```json
{
  "text": "I'll be happy to take it!"
}
```

*Response code:* `200 OK`

**Example 2.** *POST request to the `/api/tasks/1/comments` endpoint by a logged-in user:*

*Request body:*
```json
{
  "text": ""
}
```

*Response code:* `400 BAD REQUEST`

**Example 3.** *POST request to the `/api/tasks/300/comments` endpoint by a logged-in user:*

*Request body:*
```json
{
  "text": "I'll be happy to take it!"
}
```

*Response code:* `404 NOT FOUND`

**Example 4.** *GET request to the `/api/tasks` endpoint by a logged-in user:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "2",
    "title": "second task",
    "description": "another task",
    "status": "CREATED",
    "author": "user2@mail.com",
    "assignee": "none",
    "total_comments": 0
  },
  {
    "id": "1",
    "title": "new task",
    "description": "a task for anyone",
    "status": "COMPLETED",
    "author": "user1@mail.com",
    "assignee": "user2@mail.com",
    "total_comments": 1
  }
]
```

**Example 5.** *GET request to the `/api/tasks/1/comments` by a logged-in user:*

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "1",
    "task_id": "1",
    "text": "I'll be happy to take it!",
    "author": "user3@mail.com"
  }
]
```
