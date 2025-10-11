package fitnesstracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
public class Session {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String activity;
    private int duration;
    private int calories;

    @ManyToOne
    @Setter
    private Application application;

    @JsonIgnore
    private final Instant uploadedAt = Instant.now();

    @JsonProperty("application")
    public String getApplicationName() {
        return application.getName();
    }
}
