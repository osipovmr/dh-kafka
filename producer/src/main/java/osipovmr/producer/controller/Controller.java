package osipovmr.producer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic}")
    private String topic;

    @GetMapping("/start")
    public void start(@RequestParam int numberOfMessage) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfMessage; i++) {
            kafkaTemplate.send(topic, "Message number: " + i);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("На передачу {} сообщений затрачено {} миллисекунд", numberOfMessage, executionTime);
    }
}