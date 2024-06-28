package osipovmr.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osipovmr.consumer.model.Message;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
