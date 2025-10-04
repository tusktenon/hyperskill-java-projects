package account.security;

import account.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied!");
    }

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${security.bcrypt-strength}") int strength) {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // for Hyperskill tests
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/api/acct/payments").hasRole("ACCOUNTANT")
                        .requestMatchers("/api/admin/**").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.POST, "/api/auth/changepass").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole(
                                "USER", "ACCOUNTANT")
                )
                // use custom AccessDeniedHandler
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(accessDeniedHandler())
                )
                // for Postman
                .csrf(AbstractHttpConfigurer::disable)
                // for the H2 console
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                // no sessions
                .sessionManagement(sessions -> sessions
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repository) {
        return username -> repository.findByEmailIgnoreCase(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
