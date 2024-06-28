package osipovmr.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private int number;

    @GetMapping("/start")
    public ResponseEntity<Void> start() {
        for (int i = 0; i < 1000; i++) {
            kafkaTemplate.send("osipov", UUID.randomUUID().toString(), "Message number: " + number++);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
