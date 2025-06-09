package dataserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    @Value("${dataservice.id}")
    private int id;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println(Arrays.toString(args));
    }
}
