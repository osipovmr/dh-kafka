package osipovmr.consumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @KafkaListener(topics = "#{environment.getProperty('topic')}")
    public void listen(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_PARTITION) long partition,
                       @Header(KafkaHeaders.OFFSET) long offset,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       String message) {
        log.info("Получено сообщение из топика {}, partition: {}, offset: {}, key: {}, value: '{}'.",
                topic,
                partition,
                offset,
                key,
                message);
    }
}