package com.dailycodework.dreamshops.security.config;

import lombok.Getter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServer;
    @Value("${kafka.sasl.jaas.config}")
    private String config;
    @Value("${kafka.security.protocol}")
    private String securityProtocol;
    @Value("${kafka.sasl.mechanism}")
    private String mechanism;

    @Bean
    public ExecutorService kafkaExecutorService() {
        return Executors.newFixedThreadPool(5); // Adjust pool size based on load
    }
    @Bean
    public Producer<String, String> producer() {
        // Set up the producer properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServer());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


        // Add security properties for Confluent Cloud
        props.put("security.protocol", getSecurityProtocol());
        props.put("sasl.mechanism", getMechanism());
        props.put("sasl.jaas.config",getConfig());

        // Create and return the KafkaProducer
        return new KafkaProducer<>(props);
    }
}
