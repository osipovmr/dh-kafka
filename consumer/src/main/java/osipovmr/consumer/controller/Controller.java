package osipovmr.consumer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    @KafkaListener(topics = "osipov")
    public void listen(String message) {
        log.info("Получено сообщение из топика 'osipov' {}.", message);
    }
}
