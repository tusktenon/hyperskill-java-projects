package fitnesstracker.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Developer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "developer")
    @JsonIgnore
    private final Set<Application> applications = new HashSet<>();

    Developer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @JsonProperty("applications")
    public List<Application> getSortedApplications() {
        List<Application> list = new ArrayList<>(applications);
        list.sort(Comparator.comparing(Application::getRegisteredAt).reversed());
        return list;
    }
}
