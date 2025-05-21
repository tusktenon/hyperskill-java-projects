package webCalendarSpring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EventController {

    @GetMapping("/event/today")
    public List<Void> today() {
        return List.of();
    }
}
