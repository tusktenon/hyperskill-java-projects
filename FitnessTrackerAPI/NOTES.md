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

