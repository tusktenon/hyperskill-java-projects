package feedbackservice;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.MongoDBContainer;

import java.util.List;

@Component
public class MongoContainerProvider {

    private final MongoDBContainer container;

    public MongoContainerProvider() {
        container = new MongoDBContainer("mongo:5");
        container.withCreateContainerCmdModifier(cmd -> cmd.withName("feedback-service"));
        container.addEnv("MONGO_INITDB_DATABASE", "feedback_db");
        container.setPortBindings(List.of("27017:27017"));
        container.start();
    }

    @PreDestroy
    public void tearDown() {
        container.stop();
    }
}
