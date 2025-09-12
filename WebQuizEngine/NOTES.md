# Web Quiz Engine with Java: Notes

## Stage 2/6: Lots of quizzes

I chose to create a `ProposedQuiz` record type, representing a new `Quiz` that has not yet been assigned an `id`. I like the "type-correctness" of this approach, but it is also possible to work entirely with `Quiz`. Simply move the `withId()` method from `ProposedQuiz` to `Quiz`, and replace the `@JsonIgnore` annotation on the `answer` field with
```java
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
```
This annotation [specifies](https://www.javadoc.io/doc/com.fasterxml.jackson.core/jackson-annotations/latest/com.fasterxml.jackson.annotation/com/fasterxml/jackson/annotation/JsonProperty.Access.html) that the property will be written as part of deserialization (i.e., when adding a new quiz via `POST`) but not read for serialization (i.e., when sending a quiz in response to a `GET` request). For extra safety, you could also add the complementary annotation
```java
@JsonProperty(access = JsonProperty.Access.READ_ONLY)
```
on the `id` field, to ensure that, if a user happens to include an `id` in the request body when posting a new quiz, that `id` value will be ignored during deserialization (although my `QuizService` implementation would ignore such an `id` in any case).


## Stage 6/6: Advanced queries

From a design perspective, there's a good argument to be made for splitting my `models.User` class into two or three separate classes:
- a `RegistrationRequest` class (or preferably, a record) to model the JSON body of registration requests;
- a `User` JPA entity class to model users persisted in the database;
- a `UserAdapter` class that wraps a `User` object and implements the `UserDetails` interface.

This is the approach taken in the Hyperskill [Custom User Store](https://hyperskill.org/learn/step/32430) lecture and (to certain extent) in Craig Walls's *[Spring in Action, 6th edition](https://www.manning.com/books/spring-in-action-sixth-edition)* (Section 5.2.2, pp. 119-124) and in Laurentiu Spilca's *[Spring Security in Action, 2nd edition](https://www.manning.com/books/spring-security-in-action-second-edition)* (Section 3.2.5, pp. 53-55).

I'll likely adopt this design in future projects, but decided not to here. A single class fulfilling all three roles works just fine from a technical standpoint, and it's probably instructive to have at least one Spring Data + Security + Web project that proves this.
