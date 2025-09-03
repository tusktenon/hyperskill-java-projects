package taskmanagement.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    @Value("${authentication.tokenExpiration}")
    private long tokenExpiration;

    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
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
