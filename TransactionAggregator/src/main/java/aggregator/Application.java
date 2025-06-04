package aggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/aggregate")
    public String aggregate() {
        // As of Spring 6.1, RestClient is preferred to RestTemplate
        return RestClient.create("http://localhost:8889/ping")
                .get()
                .retrieve()
                .body(String.class);
    }
}