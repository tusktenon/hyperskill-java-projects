package fitnesstracker.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class ApiKeyHttpConfigurer
        extends AbstractHttpConfigurer<ApiKeyHttpConfigurer, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) {
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilterAfter(
                new ApiKeyFilter(manager), UsernamePasswordAuthenticationFilter.class);
    }
}
