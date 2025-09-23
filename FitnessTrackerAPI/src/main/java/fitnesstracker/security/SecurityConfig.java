package fitnesstracker.security;

import fitnesstracker.persistence.ApplicationRepository;
import fitnesstracker.persistence.DeveloperRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
@EnableScheduling
public class SecurityConfig {

    private final ApplicationRepository applicationRepository;
    private final DeveloperRepository developerRepository;

    public SecurityConfig(ApplicationRepository applicationRepository,
                          DeveloperRepository developerRepository) {
        this.applicationRepository = applicationRepository;
        this.developerRepository = developerRepository;
    }

    @Bean
    ApplicationRequestRateLimiter applicationRequestRateLimiter() {
        return ApplicationRequestRateLimiter.initialize(applicationRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService developerDetailsService() {
        return username -> developerRepository.findByEmail(username)
                .map(SecurityDeveloper::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    @Bean
    AuthenticationProvider developerAuthenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(developerDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationProvider apiKeyAuthenticationProvider() {
        return new ApiKeyAuthenticationProvider(applicationRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // enable basic HTTP authentication
                .httpBasic(Customizer.withDefaults())
                // enable custom API-key authentication
                .with(new ApiKeyHttpConfigurer(), Customizer.withDefaults())
                .authenticationProvider(apiKeyAuthenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // required for tests
                        .requestMatchers("/actuator/shutdown").permitAll()
                        // to prevent access errors for validation exceptions
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/developers/signup").permitAll()
                        .anyRequest().authenticated()
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
}
