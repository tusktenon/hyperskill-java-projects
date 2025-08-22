package engine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable) // for POST requests via Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll() //
                        // required for testing
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .requestMatchers("/api/quizzes/**").authenticated()
                        .anyRequest().denyAll()
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(AppUserRepository repository) {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
