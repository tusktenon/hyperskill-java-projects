package fitnesstracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class ApiKeyFilter extends OncePerRequestFilter {

    // AntPathRequestMatcher is deprecated in Spring Security 6.5. If Hyperskill updates the
    // dependencies for this project, the line below should be replaced with
    // private final RequestMatcher matcher =
    //        PathPatternRequestMatcher.withDefaults().matcher("/api/tracker");
    private final RequestMatcher matcher = new AntPathRequestMatcher("/api/tracker");

    private final AuthenticationEntryPoint authenticationEntryPoint = (request, response, e) -> {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(e.getMessage());
    };

    private final AuthenticationManager manager;

    public ApiKeyFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (matcher.matches(request)) {
            try {
                try {
                    String keyHeader = Objects.requireNonNull(request.getHeader("X-API-Key"));
                    UUID key = UUID.fromString(keyHeader);
                    Authentication authentication = new ApiKeyAuthentication(key);
                    authentication = manager.authenticate(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (NullPointerException e) {
                    throw new BadCredentialsException("API key required");
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Invalid API key: not a UUID");
                }
            } catch (AuthenticationException e) {
                authenticationEntryPoint.commence(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
