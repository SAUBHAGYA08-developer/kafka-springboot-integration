package com.example.kafka.kafkaservice.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.kafka.kafkaservice.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService{

//	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public  void sendMsgToTopic(String topic, String message, String key, long startTime) {

		try {
//			logger.info("Sending message to topic with topicName : {}", topic);
			boolean success = false;
			if (key != null && !key.isEmpty()) {
				kafkaTemplate.send(topic, key, message);
				success = true;
			} else {
				kafkaTemplate.send(topic, message);
				success = true;
			}
		} catch (Exception e) {
//			logger.error("Something wents wrong while sending message to topic : {} with key : {}", topic, key);
			e.getStackTrace();
		}

	}
}
