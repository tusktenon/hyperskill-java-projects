# Account Service (Java): Notes

## Stage 2/7: The authentication

- The instructions state that email addresses should be treated as case-insensitive, and while that's true for evaluating uniqueness, the Hyperskill tests will only pass if the email values returned in JSON response bodies are all lowercase. The easiest way to achieve this consistently is to simply to convert emails to lowercase before storing new users in the database.

- In the event that a user attempts to sign up with an email address that's already registered, the Hyperskill tests require the error message `User exist!`, not the grammatically correct `User exists!`.


## Stage 5/7: The authorization

- For the list of all users, the actual URI required by the Hyperskill tests is `api/admin/user/`, not `api/admin/user`.


## Stage 6/7: Logging events

- In analogy with the issue from Stage 5, the actual URI required by the tests for the list of all security events is `/api/security/events/`, not `/api/security/events`.

- The instructions state that we should lock an account on suspicion of a brute-force attack "if there are more than 5 consecutive attempts to enter an incorrect password," but the actual limit imposed by the tests is 4.

- Failed login attempts with an unknown email/username must be logged (though of course, they don't count towards any registered user's failed attempts).


## Stage 7/7: Securing connection

- To be clear: you need to create a `keystore` directory under the `resources` directory, `cd` into this new directory, and *then* run the provided `keytool` command.

- There's an inconsistency in the instructions between the `keytool` command and the additions to the `application.properties` file: the former creates a certificate file named `keystore.p12`, but the latter expects a file named `service.p12`. You can resolve this either way; I chose to generate a file named `service.p12`.

- Once you've completed this Stage, you can still use Postman to test your application; just use `https` instead of `http`.
