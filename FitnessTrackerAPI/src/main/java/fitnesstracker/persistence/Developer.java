package fitnesstracker.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Developer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    Developer(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
