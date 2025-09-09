package fitnesstracker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor
public class Session {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String activity;
    private int duration;
    private int calories;
    @JsonIgnore
    private final Instant uploadedAt = Instant.now();
}
