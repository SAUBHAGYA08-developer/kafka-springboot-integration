package com.example.kafka.kafkaservice.service;

import java.util.Collection;
import java.util.Map;

import org.apache.kafka.clients.admin.ListTopicsResult;

public interface KafkaAdminService {

	ListTopicsResult getTopics(long startTime);

	void deleteTopics(Collection<String> topicNames, long startTime);

	void createTopic(String topicName, int partitions, short replicationFactor, long startTime);

	Map<String, Object> describeTopicConfig(String topicName, long startTime);

	Map<String, Object> describeTopic(String topicName, long startTime);

}
