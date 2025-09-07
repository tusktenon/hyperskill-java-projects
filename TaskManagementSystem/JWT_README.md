# Account lookup with JWT authentication

In order to add a new task, we need to obtain an `Account` object corresponding to the authenticated user making the request.

In Stage 2 (where only Basic HTTP authentication was used), we could use Spring Security's `@AuthenticationPrincipal` annotation to provide the `TaskController.add()` method with a parameter of type `AccountAdapter`:
```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed,
         @AuthenticationPrincipal AccountAdapter adapter) {
    return taskRepository.save(proposed.toTask(author));
}
```

This code won't work with JWT authentication: it doesn't use our `UserDetailsService` bean, so the `Authentication` object it provides is not an `AccountAdapter`. We need another approach.

Perhaps the simplest solution is to have `TaskController.add()` take a `Principal` parameter, and use that to lookup the corresponding `Account`:
```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed, Principal principal) {
    Account author = accountRepository.findByEmailIgnoreCase(principal.getName())
            .orElseThrow();
    return taskRepository.save(proposed.toTask(author));
}
```
(Of course, we could also use an `Authentication` parameter, but `Principal` is more appropriate, since we only need the `getName()` method.)

From a design perspective, this might also be the best solution:

- It's robust: a `Principal` parameter should work with virtually any authentication mechanism. \[Though note that if we use `Principal` with Basic HTTP authentication and end up needing the full `Account` object, we'll be doing two account-by-username database lookups: the automatic one by `UserDetailsService` during authentication, and the explicit one within the controller/service method.\]
- If/when we introduce a `TaskService` class to handle the actual business logic of adding a task, we have good separation of concerns.
- Uniform use of `Principal` results in more consistent parameter types in both the `TaskController` and `TaskService` methods.
- It also makes testing `TaskController` methods easier.

However, with a bit of extra work, we *can* continue to use `@AuthenticationPrincipal` if we want.


This annotation accepts an optional `expression` element, which is a SpEL expression to be used to resolve the principal. For JWT authentication, the `Authentication` interface is fulfilled by a [`Jwt`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/jwt/Jwt.html) object, which has a [`getSubject()`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/jwt/JwtClaimAccessor.html#getSubject()) method:
```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed,
         @AuthenticationPrincipal(expression = "@userDetailsService.loadUserByUsername(#this.getSubject())")
         AccountAdapter adapter) {
    return taskRepository.save(proposed.toTask(adapter.getAccount()));
}
```

At this point, we might as well use the `expression` element to get an `Account` object directly:
```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed,
         @AuthenticationPrincipal(expression = "@userDetailsService.loadUserByUsername(#this.getSubject()).getAccount()")
         Account author) {
    return taskRepository.save(proposed.toTask(author));
}
```

This, in turn, reveals an inefficiency in trying to reuse the `UserDetailsService` bean: we're wrapping the `Account` object in an `AccountAdapter`, then immediately unwrapping it. Let's create a service class that obtains the associated `Account` from a JWT token directly:
```java
@Service
public class JwtAccountLookup {

    private final AccountRepository repository;

    public JwtAccountLookup(AccountRepository repository) {
        this.repository = repository;
    }

    public Account getAccount(JwtClaimAccessor token) {
        return repository.findByEmailIgnoreCase(token.getSubject()).orElseThrow();
    }
}
```

We can now use the auto-generated bean in the `TaskController.add` method:
```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed,
         @AuthenticationPrincipal(expression = "@jwtAccountLookup.getAccount(#this)")
         Account author) {
    return taskRepository.save(proposed.toTask(author));
}
```

Finally, we can clean this up by defining a custom annotation:

```java
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal(expression = "@jwtAccountLookup.getAccount(#this)")
public @interface AuthenticatedAccount {}
```

```java
@PostMapping("/api/tasks")
Task add(@Valid @RequestBody ProposedTask proposed, @AuthenticatedAccount Account author) {
    return taskRepository.save(proposed.toTask(author));
}
```

In developing this approach, I found [this Stack Overflow discussion](https://stackoverflow.com/questions/60727265/use-authenticationprincipal-with-jwtauthenticationtoken-to-use-own-user-class) very helpful.
