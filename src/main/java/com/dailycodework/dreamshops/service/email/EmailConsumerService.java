package com.dailycodework.dreamshops.service.email;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class EmailConsumerService {

    private final Consumer<String, String> consumer;

    public EmailConsumerService() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka server address
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "email-sender-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // Start from the earliest message

        this.consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("emails")); // Subscribe to the "emails" topic
    }

    public void processEmails() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100); // Poll for new messages
            for (ConsumerRecord<String, String> record : records) {
                // Logic to send the email
                sendEmail(record.key(), record.value());
                System.out.println("Email sent to: " + record.key());
            }
            consumer.commitAsync(); // Commit offsets asynchronously for better performance
        }
    }

    private void sendEmail(String email, String message) {
        // Implement actual email-sending logic here
    }
}

