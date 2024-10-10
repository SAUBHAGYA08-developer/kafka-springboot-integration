package com.example.kafka.kafkaservice.service;

import org.springframework.messaging.handler.annotation.Payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ConsumerService {

	public void handleMessage(@Payload String message) throws JsonMappingException, JsonProcessingException;

}
