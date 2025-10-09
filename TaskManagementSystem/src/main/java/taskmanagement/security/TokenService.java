package taskmanagement.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    private final long tokenExpiration;
    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder,
                        @Value("${authentication.tokenExpiration}") long tokenExpiration) {
        this.encoder = encoder;
        this.tokenExpiration = tokenExpiration;
    }

    public String generateToken(String username) {
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(tokenExpiration, ChronoUnit.SECONDS))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
