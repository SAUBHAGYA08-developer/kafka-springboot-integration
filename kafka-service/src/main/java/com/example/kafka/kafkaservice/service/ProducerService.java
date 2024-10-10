package com.example.kafka.kafkaservice.service;

public interface ProducerService {

	 void sendMsgToTopic(String topic, String message, String key, long startTime);

}
