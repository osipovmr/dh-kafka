package osipovmr.consumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RestController;
import osipovmr.consumer.model.Message;
import osipovmr.consumer.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final MessageRepository repository;
    private final List<Message> messages = new ArrayList<>();

    @KafkaListener(topics = "osipov")
    public void listen(@Header(KafkaHeaders.OFFSET) long offset,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) long partition,
                       @Header(KafkaHeaders.RECEIVED_KEY) UUID key,
                       String message) {
        log.info("Получено сообщение из топика 'osipov', partition: {}, offset: {}, key: {}, value: {}.",
                partition,
                offset,
                key,
                message);
        if (partition == 1) {
            Message entity = Message.builder()
                    .uuid(key)
                    .offset(offset)
                    .partition(partition)
                    .message(message)
                    .build();
            messages.add(entity);
            if (messages.size() > 1000) {
                repository.saveAll(messages);
                messages.clear();
            }
        }
    }
}
