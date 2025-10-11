package recipes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.function.BiPredicate;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BiPredicate<Long, SecurityChef> recipeAuthorIsPrincipal(RecipeRepository repository) {
        return (recipeId, principal) -> {
            Recipe recipe = repository.findById(recipeId)
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
            return Objects.equals(recipe.getAuthor().getId(), principal.getChef().getId());
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(ChefRepository repository) {
        return username -> repository.findByEmail(username)
                .map(SecurityChef::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }
}
