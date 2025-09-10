package fitnesstracker.security;

import fitnesstracker.persistence.DeveloperRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // enable basic HTTP authentication
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // required for tests
                        .requestMatchers("/actuator/shutdown").permitAll()
                        // to prevent access errors for validation exceptions
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/developers/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/developers/*").authenticated()
                        .requestMatchers("/api/tracker").permitAll()
                )
                // for testing with Postman
                .csrf(AbstractHttpConfigurer::disable)
                // for the H2 console
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                // don't use sessions
                .sessionManagement(
                        sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(DeveloperRepository repository) {
        return username -> repository.findByEmail(username)
                .map(SecurityDeveloper::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
