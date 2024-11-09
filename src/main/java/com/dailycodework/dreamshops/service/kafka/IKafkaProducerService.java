package com.dailycodework.dreamshops.service.kafka;

public interface IKafkaProducerService {
    void sendMessage(String topic, String key, String message);
}
