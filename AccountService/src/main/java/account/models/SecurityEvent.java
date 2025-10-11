package account.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@NoArgsConstructor
@Getter
public class SecurityEvent {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    private Instant date;
    private Action action;
    private String subject;
    private String object;
    private String path;

    public SecurityEvent(Action action, String subject, String object, String path) {
        this.date = Instant.now();
        this.action = action;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    public enum Action {
        CREATE_USER, CHANGE_PASSWORD, ACCESS_DENIED, LOGIN_FAILED,
        GRANT_ROLE, REMOVE_ROLE, LOCK_USER, UNLOCK_USER, DELETE_USER, BRUTE_FORCE
    }
}
