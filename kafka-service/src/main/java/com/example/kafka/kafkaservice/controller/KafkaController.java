package com.example.kafka.kafkaservice.controller;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafka.kafkaservice.service.KafkaAdminService;
import com.example.kafka.kafkaservice.service.ProducerService;

/*
	 * if is there any issue email on 
	 * en21ca501161@medicaps.ac.in
	 * ssaubhagya2129@gmail.com
  	 * linkedIn : https://www.linkedin.com/in/saubhagya08/ 
	 * Project: Kafka Implementation using Spring Boot
	 * Description: Created a Kafka implementation using Java 8 and Spring Boot 2.3.10.RELEASE, 
	 * utilizing binding to integrate Kafka with Spring Boot. This project enables 
	 * seamless message production and consumption using Kafka topics, 
	 * leveraging Spring Boot's auto-configuration and binding capabilities.
         * Reference : https://kafka.apache.org/
		
		====>>> Key Technologies <<<====
		> Kafka
		> Spring Boot 2.3.10.RELEASE
		> Java 8
		====>>> Functionality <<<====
		> Kafka topic production and consumption
		> Spring Boot auto-configuration and binding for Kafka
		> Message handling and processing using Kafka

   		====>>> About Kafka <<<====
     		Here's a brief description of Kafka and its uses:
		What is Apache Kafka?
		Apache Kafka is an open-source, distributed streaming platform designed for high-throughput and fault-tolerant data processing. 
		It enables real-time data integration, processing, and analytics.
		Key Features:
		Publish-Subscribe Model: Producers publish messages to topics, and consumers subscribe to topics to receive messages.
		Distributed Architecture: Scalable, fault-tolerant, and highly available.
		High-Performance: Handles massive data volumes with low latency.
		Data Durability: Messages are persistent and replayable.
		Why Use Kafka?
		Real-Time Data Integration: Unify data from multiple sources.
		Event-Driven Architecture: Enable microservices communication.
		Streaming Analytics: Process and analyze data in real time.
		Log Aggregation: Centralize log collection and processing.
		IoT Data Processing: Handle high-volume sensor data.
		Common Use Cases:
		Data Pipelining: LinkedIn, Uber
		Real-Time Analytics: Netflix, Spotify
		Log Processing: Twitter, Airbnb
		IoT Applications: Industrial automation, Smart cities
		Kafka's versatility and scalability make it popular for various industries and applications.

		Managed Services for Running Kafka in Live Applications: Top Options for Learning
		Discover reliable and scalable solutions for integrating Kafka into your live applications with these popular managed services:
		Confluent Cloud:  https://www.confluent.io/confluent-cloud/
		Amazon Web Services (AWS) Managed Streaming for Kafka (MSK): https://aws.amazon.com/msk/
		DigitalOcean Managed Databases for Kafka: https://www.digitalocean.com/products/managed-databases-kafka
		These services offer streamlined Kafka deployment, management, and scaling for seamless integration into your live applications.
	 */
@RestController
public class KafkaController {

	@Autowired
	private KafkaAdminService kafkaAdminService;

	@Autowired
	private ProducerService producerService;
	
	@PostMapping("/publishMessage")
	public void publishMessage(@RequestParam String topic, @RequestBody String message,
			@RequestParam(value = "key", required = false) String key) {
		long startTime = System.currentTimeMillis();
		try {
			producerService.sendMsgToTopic(topic, message, key, startTime);
		} catch (Exception e) {

		}
	}

	@GetMapping("/getTopics")
	public ListTopicsResult getTopics() {
		long startTime = System.currentTimeMillis();
		try {
			return kafkaAdminService.getTopics(startTime);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@PostMapping("/deleteTopics")
	public void deleteTopics(@RequestBody Collection<String> topicNames) {
		long startTime = System.currentTimeMillis();
		try {
			kafkaAdminService.deleteTopics(topicNames, startTime);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@PostMapping("/createTopic")
	public void createTopic(@RequestParam String topicName, @RequestParam int partitions,
			@RequestParam short replicationFactor) {
		long startTime = System.currentTimeMillis();
		try {
			kafkaAdminService.createTopic(topicName, partitions, replicationFactor, startTime);
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	@GetMapping("/getTopicDescription")
	public Map<String, Object> getTopicDescription(@RequestParam String topicName) {
		long startTime = System.currentTimeMillis();
		try {
			return kafkaAdminService.describeTopic(topicName, startTime);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	@GetMapping("/getTopicConfig")
	public Map<String, Object> getTopicConfig(@RequestParam String topicName) {
		long startTime = System.currentTimeMillis();
		try {
			return kafkaAdminService.describeTopicConfig(topicName, startTime);
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

}
