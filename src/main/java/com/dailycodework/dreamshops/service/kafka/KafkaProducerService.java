package com.dailycodework.dreamshops.service.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService implements IKafkaProducerService {

    private final Producer<String, String> producer;

    @Autowired
    public KafkaProducerService(Producer<String, String> producer) {
        this.producer = producer;
    }

    @Override
    public void sendMessage(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);

        CompletableFuture.runAsync(() ->
                producer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.println("Message sent successfully with metadata: " + metadata.toString());
            } else {
                System.err.println("Error sending message: " + exception.getMessage());
            }
        }) );

    }

    public void closeProducer() {
        producer.close();
    }
}
