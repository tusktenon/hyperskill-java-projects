package fitnesstracker.persistence;

import com.fasterxml.jackson.annotation.*;
import fitnesstracker.presentation.Views;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    private String name;

    @Column(nullable = false)
    private String description;

    @JsonView(Views.ApplicationViews.RegistrationSummary.class)
    @JsonProperty("apikey")
    private final UUID apiKey = UUID.randomUUID();

    @ManyToOne
    @JsonIgnore
    private Developer developer;

    @JsonIgnore
    private final Instant registeredAt = Instant.now();

    Application(String name, String description, Developer developer) {
        this.name = name;
        this.description = description;
        this.developer = developer;
    }
}
