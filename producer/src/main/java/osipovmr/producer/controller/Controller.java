package osipovmr.producer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${number}")
    private int number;

    @GetMapping("/start")
    public ResponseEntity<Void> start() {
        for (int i = 0; i < number; i++) {
            kafkaTemplate.send("osipov", UUID.randomUUID().toString(), "Message number: " + i);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
