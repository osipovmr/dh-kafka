package osipovmr.producer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${topic}")
    private String topic;

    @PostMapping("/kafka")
    public void kafka(@RequestParam int numberOfMessage) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfMessage; i++) {
            kafkaTemplate.send(topic, "Message number: " + i);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("На передачу {} сообщений через kafka затрачено {} миллисекунд", numberOfMessage, executionTime);
    }

    @PostMapping("/web")
    public void web(@RequestParam int numberOfMessage) {
        RestTemplate restTemplate = new RestTemplate();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfMessage; i++) {
            restTemplate.postForEntity("http://consumer1:8082/web", new HttpEntity<>("Message number: " + i, getHeaders()), Void.class);
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("На передачу {} сообщений через web затрачено {} миллисекунд", numberOfMessage, executionTime);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        return headers;
    }
}