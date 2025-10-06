package account.repositories;

import account.models.SecurityEvent;
import account.models.SecurityEvent.Action;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SecurityEventRepository extends CrudRepository<SecurityEvent, Long> {

    List<SecurityEvent> findAllByOrderById();
}
