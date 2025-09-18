# Fitness Tracker API: Notes

## Stage 3/5: Application registration

### API key generation

I implemented the API key as a Version 4 UUID, generated with `UUID.randomUUID()`. Another option, more in line with approach suggested in the instructions, is to use the [`SecureRandom`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/SecureRandom.html) class or, equivalently, the [`KeyGenerators`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/crypto/keygen/KeyGenerators.html) class from the Spring Security Crypto module:
```java
SecureRandom secureRandom = new SecureRandom();
byte[] bytes = new byte[16]; // 16 bytes = 128-bit key
secureRandom.nextBytes(bytes);
String key = HexFormat.of().formatHex(bytes); // 16 bytes as hex string

// or...

byte[] bytes = KeyGenerators.secureRandom(16).generateKey();
String key = new BigInteger(1, bytes).toString(16);
```

This is also the approach taken in the Hyperskill lecture [Custom authentication: Configuration](https://hyperskill.org/learn/step/38745#token-generation). I don't know if one option (Version 4 UUID or random bytes produced by [`SecureRandom`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/security/SecureRandom.html)) is preferable to the other in this situation, but since the API keys are assigned permanently and it's essential that no two applications have the same key, perhaps UUID is more appropriate?

### Eager vs. lazy fetching of a `@OneToMany` field

Consider the `DeveloperController.getProfile()` method. At first glance, it would seem that the following implementation should work:
```java
@GetMapping("/{id}")
@PreAuthorize("#securityDeveloper.developer.id == #id")
public Developer getProfile(
        @PathVariable long id, @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
    return securityDeveloper.getDeveloper();
}
```

The problem is that the `Developer.applications` field is fetched lazily, so we'll encounter a `LazyInitializationException`.

Simply adding the `@Transactional` annotation does not work: this annotation applies to the code within the method body, not to any database queries done as part of authentication.

One solution is to switch to eager initialization, by adding the `fetch = FetchType.EAGER` element to the `@OneToMany` annotation above the `Developer.applications` declaration. But this is a terrible idea in terms of overall application performance: most `Developer` lookups don't require the set of applications (in particular, consider that a `Developer` lookup is performed by the `UserDetailsService` bean for *every* request to *any* authenticated endpoint).

Instead, we opt to do a second, eager lookup of the current developer:
```java
@GetMapping("/{id}")
@PreAuthorize("#securityDeveloper.developer.id == #id")
@Transactional
public Developer getProfile(
        @PathVariable long id, @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
    return repository.findById(id).orElseThrow();
}
```

We could avoid doing two queries, perhaps by defining an alternative, eager-lookup implementation of `UserDetailsService` and selecting it with the `expression` element of `@AuthenticationPrincipal`. However, we'd then be performing the expensive, eager lookup of the developer with applications, before even checking if the requesting developer's ID matches the path variable. It seems reasonable to first perform a relatively fast lookup (without applications) and checking that the IDs match before proceeding to the slower query.


## Stage 4/5: API-key authentication

### The odd API design of the `GET /api/tracker` endpoint

In light of the new functionality added in Stage 4, it seems strange that the behaviour of the `GET /api/tracker` endpoint, and its implementation via the `SessionController.getSessions` method, should remain unchanged from Stage 1:
```java
@GetMapping
public List<Session> getSessions() {
    return repository.findAllByOrderByUploadedAtDesc();
}
```
That is, having authenticated the `GET` request as having been made by a specific application, we still return the list of *all* tracker data from *all* applications. I would have thought this endpoint should now return only the tracker data for the requesting application:
```java
@GetMapping
public List<Session> getSessions(@AuthenticationPrincipal Application application) {
    return repository.findAllByApplicationOrderByUploadedAtDesc(application);
}
```
However, the instructions for Stage 4 make no mention of such a change, and indeed, the Hyperskill tests will pass with the first version of `getSessions` above, but not the second.

### Completing Stage 4 using basic Spring Web features

The instructions for this stage suggest "you might want to build a custom security filter and add it to the existing security filter chain." Certainly, completing Stage 4 in this manner provides considerable learning value. However, it's entirely possible to just use Spring Web (in particular, the `@RequestHeader` annotation) to implement the required behaviour, without needing to make a single change to the security configuration from Stage 3. All that's required is a few extra lines of code in the `SessionController` class:
```java
@RestController
@RequestMapping("/api/tracker")
public class SessionController {

    private final ApplicationRepository applicationRepository;
    private final SessionRepository sessionRepository;

    public SessionController(ApplicationRepository applicationRepository,
                             SessionRepository sessionRepository) {
        this.applicationRepository = applicationRepository;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public List<Session> getSessions(
            @RequestHeader(name = "X-API-Key", required = false) String keyHeader) {
        Application application = getApplicationByApiKey(keyHeader);
        return sessionRepository.findAllByOrderByUploadedAtDesc();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addSession(@RequestHeader(name = "X-API-Key", required = false) String keyHeader,
                           @RequestBody Session session) {
        Application application = getApplicationByApiKey(keyHeader);
        session.setApplication(application);
        sessionRepository.save(session);
    }

    private Application getApplicationByApiKey(String keyString) {
        try {
            UUID key = UUID.fromString(Objects.requireNonNull(keyString));
            return applicationRepository.findByApiKey(key).orElseThrow();
        } catch (NullPointerException | IllegalArgumentException | NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
```
Compare the ease of this approach with defining and using a custom security filter, which requires four new classes!

### Completing Stage 4 using Spring Security

For defining a custom security filter and then configuring the filter chain to use it, the essential references are the [Custom authentication: Creating components](https://hyperskill.org/learn/step/38734) and [Custom authentication: Configuration](https://hyperskill.org/learn/step/38745) lectures.

1. For an explanation of the rather strange-looking implementation of the `ApiKeyAuthentication.setAuthenticated` method, see the official documentation for the [`Authentication`](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/Authentication.html) interface's `setAuthenticated` and `isAuthenticated` methods. Implementations of `setAuthenticated` should always allow this method to be called with a `false` parameter, but can (depending on the logic of the implementation) reject calls with a `true` parameter. In our case, `Authentication` objects are immutable, and there's no valid reason to change the authentication status of an existing token: we'd simply create a new one instead.

2. I found myself wondering why we needed a custom configurer class (my `ApiKeyHttpConfigurer`, corresponding to the `MyConfigurer` class from the [lecture](https://hyperskill.org/learn/step/38745#configuring-the-filter-chain)). Why can't we just add the `ApiKeyFilter` and `ApiKeyAuthenticationProvider` directly in the `SecurityConfig.SecurityFilterChain` bean, like so?
    ```java
    public class SecurityConfig {
        //...
        
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            AuthenticationManager manager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterAfter(new ApiKeyFilter(manager), UsernamePasswordAuthenticationFilter.class)
                    .authenticationProvider(provider);

            return http
                    .httpBasic(Customizer.withDefaults())
                    .authorizeHttpRequests(auth -> auth
            // ...
    ```
It turns out that the `AuthenticationManager` object isn't accessible during `SecurityFilterChain` building, and the `manager` variable obtained above is simply `null`.

3. We're still using Basic HTTP authentication for most endpoints. Note that we now have to explicitly add our `UserDetailsService` bean while building the `HttpSecurity` object used to define our `SecurityFilterChain` (to be honest, I don't know why this is so). That being the case, I decided to move the `UserDetailsService` implementation from a lambda-defined bean in `SecurityConfig` to its own named class. This change results in a better matching pair of fields for `SecurityConfig` (an `AuthenticationProvider` has a closer semantic relationship to a `UserDetailsService` than to a `CrudRepository`), and also makes it clear that this `UserDetailsService` applies to `Developer`, not `Application`.

