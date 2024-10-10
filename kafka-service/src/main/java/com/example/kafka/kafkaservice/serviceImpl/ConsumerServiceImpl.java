package com.example.kafka.kafkaservice.serviceImpl;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.example.kafka.kafkaservice.bindings.MyStreamBindings;
import com.example.kafka.kafkaservice.service.ConsumerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsumerServiceImpl implements ConsumerService{
	
    @StreamListener(MyStreamBindings.INPUT)
    public void handleMessage(@Payload String message) throws JsonMappingException, JsonProcessingException {
        // Process the received message
    	ObjectMapper mapper = new ObjectMapper();
        try {
			doSomeOpeartion(mapper.readTree(message));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doSomeOpeartion(JsonNode readTree) {
		// write a code for performing operation as per your logic
	}

}
