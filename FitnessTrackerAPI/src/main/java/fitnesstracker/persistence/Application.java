package fitnesstracker.persistence;

import com.fasterxml.jackson.annotation.*;
import fitnesstracker.presentation.Views;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    private String name;

    @Column(nullable = false)
    private String description;

    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    @JsonProperty("apikey")
    private final UUID apiKey = UUID.randomUUID();

    @Column(nullable = false)
    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    private Category category;

    @ManyToOne
    @JsonIgnore
    private Developer developer;

    @JsonIgnore
    private final Instant registeredAt = Instant.now();

    Application(String name, String description, Category category, Developer developer) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.developer = developer;
    }

    public enum Category {
        BASIC, PREMIUM;

        @JsonCreator
        public static Category fromString(String s) {
            return s == null ? null : valueOf(s.toUpperCase());
        }

        @JsonValue
        public String toJsonString() {
            return name().toLowerCase();
        }
    }
}
