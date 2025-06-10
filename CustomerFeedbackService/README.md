# Customer Feedback Service

## Project description

There are many market places on the Internet and customers often rate goods they buy. This project offers you an opportunity to try yourself in building a web service for saving, storing and finding customer feedback data using Spring Boot and the popular MongoDB database.

[View more](https://hyperskill.org/projects/409)


## Stage 1/4: Master of documents

### Description

MongoDB is a well-known document database that is made easy to use in your projects, thanks to excellent support from Spring Data.

MongoDB stores data as JSON documents, which are grouped into collections. You can use Spring Data annotations to map Java classes to these JSON documents. To gain insights, check out this official [Spring tutorial](https://spring.io/guides/gs/accessing-data-mongodb/).

> [!NOTE]
> This project requires Docker on your machine to be able to create and start MongoDB containers. If you haven't installed Docker yet, follow [these official instructions](https://docs.docker.com/engine/install/) suitable for your operating system. This project uses the `mongo:5` Docker image; thus, you can pull it ahead of time to speed up the application's initial start.

To make a MongoDB container run upon the application startup and stop upon the application shutdown, you can add this component to the project:
```java
@Component
class MongoContainerProvider {
    private final MongoDBContainer container;

    public MongoContainerProvider() String mongodbImage) {
        container = new MongoDBContainer("mongo:5"); // image name
        container.withCreateContainerCmdModifier(cmd -> cmd.withName("feedback-service")); // container name
        container.addEnv("MONGO_INITDB_DATABASE", "feedback_db"); // init database
        container.setPortBindings(List.of("27017:27017")); // expose port 27017
        container.start();
    }

    @PreDestroy
    public void tearDown() {
        container.stop();
    }
}
```

When the application is started, you can access the running container through the command line or a GUI MongoDB client such as Compass.

To access MongoDB via Compass, use the following connection string: `mongodb://localhost:27017/?directConnection=true`.

Accessing MongoDB via the command line is also straightforward. Start the mongo shell by typing and executing the following command:
```sh
docker exec -it feedback-service mongosh
```

When you stop the application, it automatically shuts down and removes the MongoDB container as well.

To include MongoDB support in your project, add the `spring-boot-starter-data-mongodb` starter to the project build file. In this project, the starter is already added, so you don't need to do anything. It's recommended for you to use `MongoRepository` to manipulate entities in this project.

Don't forget to configure the `application.properties` for Spring Data:
```text
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=feedback_db
```

In this stage, you will ensure the service receives customer feedback data via the REST controller, then save this data in the database. Store all documents in a single collection named `feedback`. You can do this by specifying the collection's name in your entity's `@Document` annotation:
```java
@Document(collection = "feedback")
public class MyEntity {
    @Id
    private String id;
    // other fields
}
```

### Objectives

- Create a `POST /feedback` endpoint that accepts requests with JSON bodies in the following format:
    ```text
    {
      "rating": <integer>,
      "feedback": <string, optional>,
      "customer": <string, optional>,
      "product": <string>,
      "vendor": <string>
    }
    ```
    Here, `rating` is the number of "stars", from 1 to 5, assigned by the customer to a product, `feedback` is an optional customer message related to the rating, `customer` is the customer's name (if agreed to disclose), `product` is the rated product's name, and `vendor` is the product vendor's name. Both `feedback` and `customer` fields are optional and may not appear in the request body. The endpoint should respond with the status code `201 CREATED`, and the `Location` header should contain the URL of the new document:
    ```text
    Location: /feedback/<id>
    ```
    Use the `ObjectId` string representation as the document's id.

- Save all received feedback data as MongoDB documents in the `feedback` collection in the database. You are free to choose any structure for the documents, as long as each uploaded data item is saved as a single document.

### Examples

**Example 1:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "feedback": "good but expensive",
  "customer": "John Doe",
  "product": "MacBook Air",
  "vendor": "Online Trade LLC"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b88
```

**Example 2:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "product": "Blue duct tape",
  "vendor": "99 Cents & Co."
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b89
```


## Stage 2/4: Documents for all

### Description

The service can already store customer feedback. Now, you need to add functionality to fetch and display stored documents. Earlier, you made the service return the URL of each created document. Now, you should create an endpoint that will accept such a URL and return the related document. Moreover, you will create a separate endpoint for fetching all stored documents.

To reach these goals, `MongoRepository` offers two methods, `findById` and `findAll`. The latter has an overloaded version that accepts a `Sort` object to arrange the queried collection.

### Objectives

- Build a `GET /feedback/<id>` endpoint that will return the requested feedback document by its ID. This endpoint matches the document's URL you return from the `POST /feedback` endpoint. If the provided id is correct, this endpoint should respond with the status code `200 OK` and a JSON response body in the following format:
    ```text
    {
      "id": <string>,
      "rating": <integer>,
      "feedback": <string | null>,
      "customer": < | null>,
      "product": <string>,
      "vendor": <string>
    }
    ```
    If the `feedback` and/or `customer` fields are not in the document, they should show as null in the response body. If no document matches the provided `id`, the endpoint should respond with the status code `404 NOT FOUND`.

- Build a `GET /feedback` endpoint that will return a JSON array of all saved documents, arranged by their `ObjectId` in descending order. This endpoint should always respond with the status code `200 OK` and a JSON array as the response body:
    ```text
    [
    {
        "id": <string>,
        "rating": <integer>,
        "feedback": <string | null>,
        "customer": <string | null>,
        "product": <string>,
        "vendor": <string>
    },
    {
        "id": <string>,
        "rating": <integer>,
        "feedback": <string | null>,
        "customer": <string | null>,
        "product": <string>,
        "vendor": <string>
    },
    ...
    ]
    ```
    If the `feedback` collection contains no documents, this endpoint should return an empty JSON array.

### Examples

**Example 1:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "feedback": "good but expensive",
  "customer": "John Doe",
  "product": "MacBook Air",
  "vendor": "Online Trade LLC"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b88
```

**Example 2:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "product": "Blue duct tape",
  "vendor": "99 Cents & Co."
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b89
```

**Example 3:** `GET` request to the `/feedback/655e0c5f76a1e10ce2159b88` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "id": "655e0c5f76a1e10ce2159b88",
  "rating": 4,
  "feedback": "good but expensive",
  "customer": "John Doe",
  "product": "MacBook Air",
  "vendor": "Online Trade LLC"
}
```

**Example 4:** `GET` request to the `/feedback/655e0c5f76a1e10ce2159b90` endpoint.

*Response code:* `404 NOT FOUND`

**Example 5:** `GET` request to the `/feedback` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
[
  {
    "id": "655e0c5f76a1e10ce2159b89",
    "rating": 4,
    "feedback": null,
    "customer": null,
    "product": "Blue duct tape",
    "vendor": "99 Cents & Co."
  },
  {
    "id": "655e0c5f76a1e10ce2159b88",
    "rating": 4,
    "feedback": "good but expensive",
    "customer": "John Doe",
    "product": "MacBook Air",
    "vendor": "Online Trade LLC"
  }
]
```


## Stage 3/4: Turn the page

### Description

After collecting feedback from customers for some time, we've noticed a considerable increase in the number of documents. Retrieving all documents at once is not practical anymore due to the high traffic and memory utilization.

At this stage, you'll implement paging, allowing a client to request a particular portion of the document collection by specifying the desired page and page size.

`MongoRepository` extends the `PagingAndSortingRepository` repository. This implies that the overloaded `findAll` method can accept a `Pageable` object, which facilitates the simultaneous application of sorting and paging. This method gives back a `Page<T>` object carrying all the necessary information for clients to move between pages.

### Objectives

- Alter the `GET /feedback` endpoint. It should now support paging and accept optional `page` and `perPage` request parameters, with default values of 1 and 10 respectively. The `page` parameter signifies the requested page of data and the `perPage` parameter defines the number of documents per page. If either or both of these parameters are absent, apply their default values. The documents must still be sorted by their IDs in descending order before pagination.

- Validate and sanitize the `page` request parameter to ensure that only valid pagination settings are applied. Page numbering starts with 1, which means that if the given `page` value is less than 1, the first page should be returned.

- Validate and sanitize the `perPage` request parameter to ensure that only valid pagination settings are applied. The minimum allowed `perPage` value is 5 and the maximum is 20. This implies that if the provided `perPage` value is less than 5 or more than 20, the default page size of 10 should be applied.

- Change the response body of the `GET /feedback` endpoint. It should now be a JSON object with the following fields:
    ```text
    {
      "total_documents": <long>,
      "is_first_page": <boolean>,
      "is_last_page": <boolean>,
      "documents": <[array of documents]>
    }
    ```
    Where `total_documents` is the total count of documents in the collection, `is_first_page` depicts whether the returned page is the first one, `is_last_page` indicates if the returned page is the final one, and `documents` is a JSON array of JSON objects representing feedback documents in the same format as in the previous stage:
    ```text
    {
    "id": <string>,
    "rating": <integer>,
    "feedback": <string | null>,
    "customer": <string | null>,
    "product": <string>,
    "vendor": <string>
    }
    ```
    - If the requested page number is greater than the maximum available page number, the document array should be empty.

### Examples

**Example 1:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "feedback": "good but expensive",
  "customer": "John Doe",
  "product": "MacBook Air",
  "vendor": "Online Trade LLC"
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b88
```

**Example 2:** `POST` request to the `/feedback` endpoint.

*Request body:*
```json
{
  "rating": 4,
  "product": "Blue duct tape",
  "vendor": "99 Cents & Co."
}
```

*Response code:* `201 CREATED`

*Response header:*
```text
Location: /feedback/655e0c5f76a1e10ce2159b89
```

**Example 3:** `GET` request to the `/feedback` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "total_documents": 2,
  "is_first_page": true,
  "is_last_page": true,
  "documents": [
    {
      "id": "655e0c5f76a1e10ce2159b89",
      "rating": 4,
      "feedback": null,
      "customer": null,
      "product": "Blue duct tape",
      "vendor": "99 Cents & Co."
    },
    {
      "id": "655e0c5f76a1e10ce2159b88",
      "rating": 4,
      "feedback": "good but expensive",
      "customer": "John Doe",
      "product": "MacBook Air",
      "vendor": "Online Trade LLC"
    }
  ]
}
```

**Example 4:** `GET` request to the `/feedback?page=2&perPage=3` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "total_documents": 2,
  "is_first_page": true,
  "is_last_page": true,
  "documents": [
    {
      "id": "655e0c5f76a1e10ce2159b89",
      "rating": 4,
      "feedback": null,
      "customer": null,
      "product": "Blue duct tape",
      "vendor": "99 Cents & Co."
    },
    {
      "id": "655e0c5f76a1e10ce2159b88",
      "rating": 4,
      "feedback": "good but expensive",
      "customer": "John Doe",
      "product": "MacBook Air",
      "vendor": "Online Trade LLC"
    }
  ]
}
```


## Stage 4/4: Filter them all

### Description

Even though getting paged results is good, sometimes it becomes a rather long task to browse through multiple pages to find the documents you need. To make this task easier, you can apply filtration based on certain criteria, and then sort and paginate the refined collection.

This stage involves modifying the `GET /feedback` endpoint by adding a filter. This filter allows clients to apply a combination of several constraints and then retrieve a page of content from the filtered document set.

If using `MongoRepository`, filtering a collection isn't a challenging task. `MongoRepository` extends the `QueryByExampleExecutor` interface, allowing you to create a probe that serves as an `Example` for filtering documents. The `findAll` method can accept both an `Example` and a `Pageable` object, making filtering, sorting, and pagination a single-step process.

The [Spring documentation](https://docs.spring.io/spring-data/relational/reference/query-by-example.html) provides a detailed explanation on how to use Query by Example with a relational database. This approach, however, can also be applied to `MongoRepository`.

### Objectives

- Update the `GET /feedback` endpoint, so that alongside the pagination functionality, it could also accept optional filter parameters. These filters will filter the document collection based on `rating`, `customer`, `product`, and `vendor` parameters and should work in any combination with the `page` and `perPage` parameters.

- The `GET /feedback` endpoint should return only those documents that match the provided parameters for the corresponding fields. For instance, if the request made is: `GET /feedback?rating=5&product=blue+duct+tape&vendor=Total+Sales`, the endpoint should only return documents related to the product `"blue duct tape"`, with the `rating` as `5` and `vendor` as `"Total Sales"`.

- Sorting and paging settings should be applied as in the previous stages, provided that now the total number of documents and the position of the requested page should be calculated for the filtered collection of documents.

### Examples

**Example 1:** Sending a `GET` request to the `/feedback` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "total_documents": 3,
  "is_first_page": true,
  "is_last_page": true,
  "documents": [
    {
      "id": "655e0c5f76a1e10ce2159b89",
      "rating": 5,
      "feedback": null,
      "customer": null,
      "product": "Blue duct tape",
      "vendor": "99 Cents & Co."
    },
    {
      "id": "655e0c5f76a1e10ce2159b88",
      "rating": 4,
      "feedback": "good but expensive",
      "customer": "John Doe",
      "product": "MacBook Air",
      "vendor": "Online Trade LLC"
    },
    {
      "id": "655e0c5f76a1e10ce2159b87",
      "rating": 5,
      "feedback": null,
      "customer": "John Doe",
      "product": "Blue duct tape",
      "vendor": "99 Cents & Co."
    },
  ]
}
```

**Example 2:** Sending a `GET` request to `/feedback?page=1&perPage=5&customer=John+Doe&rating=5` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "total_documents": 1,
  "is_first_page": true,
  "is_last_page": true,
  "documents": [
    {
      "id": "655e0c5f76a1e10ce2159b87",
      "rating": 5,
      "feedback": null,
      "customer": "John Doe",
      "product": "Blue duct tape",
      "vendor": "99 Cents & Co."
    },
  ]
}
```

**Example 3:** Making a `GET` request to the `/feedback?page=1&perPage=5&customer=John+Doe&rating=5&product=Blue+duct+tape` endpoint.

*Response code:* `200 OK`

*Response body:*
```json
{
  "total_documents": 0,
  "is_first_page": true,
  "is_last_page": true,
  "documents": []
}
```
