package com.example.kafka.kafkaservice.serviceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.clients.admin.ConfigEntry;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.config.ConfigResource.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.kafka.kafkaservice.service.KafkaAdminService;

@Service
public class KafkaAdminServiceImpl implements KafkaAdminService {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.properties.sasl.jaas.config}")
	private String jaasConfig;

	@Value("${spring.kafka.properties.security.protocol}")
	private String securityProtocol;

	@Value("${spring.kafka.properties.sasl.mechanism}")
	private String saslMechanism;

	@Autowired
	private AdminClient adminClient;

	/*
	 *  Kafka admin methods
	 * 
	 * 
	 * 
	 */
	public void createTopic(String topicName, int numPartitions, short replicationFactor, long startTime) {
		AdminClient adminClient = AdminClient.create(getAdminConfig());
		try {
			NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
			adminClient.createTopics(Collections.singletonList(newTopic)).all().get();

		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public ListTopicsResult getTopics(long startTime) {

		try {
//			logger.info("Fetching topic list ");
			ListTopicsResult topicsResult = adminClient.listTopics();
			return topicsResult;
		} catch (Exception e) {
//			logger.error("Something wents wrong while fetching topic list");
			e.getStackTrace();
		}
		return null;
	}

	public void deleteTopics(Collection<String> topicNames, long startTime) {
		try {
//			logger.info(">> Deleting topic's...");
			DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(topicNames);
			deleteTopicsResult.all().get(); // Wait for the delete to complete
		} catch (Exception e) {
//			logger.error("Something wents wrong while deleting topic");
			e.getStackTrace();
		}
	}

	public Map<String, Object> describeTopic(String topicName, long startTime) {
		Map<String, Object> topicInfo = null;
		AdminClient adminClient = AdminClient.create(getAdminConfig());
		try {
//			logger.info(">> Describing topic with topic name : {}", topicName);

			DescribeTopicsResult describeTopicsResult = adminClient
					.describeTopics(Collections.singletonList(topicName));
			TopicDescription topicDescription = describeTopicsResult.all().get().get(topicName);

			topicInfo = new HashMap<>();
			topicInfo.put("topicName", topicName);
			topicInfo.put("internal", topicDescription.isInternal());
			topicInfo.put("partitionsCount", topicDescription.partitions().size());
			topicInfo.put("topicId", topicDescription.topicId());
			topicInfo.put("replicationFactor", topicDescription.partitions().get(0).replicas().size());

			Map<Integer, Object> partitions = topicDescription.partitions().stream()
					.collect(Collectors.toMap(partitionInfo -> partitionInfo.partition(), partitionInfo -> {
						Map<String, Object> partitionDetails = new HashMap<>();
						partitionDetails.put("replicas",
								partitionInfo.replicas().stream().map(node -> node.id()).collect(Collectors.toList()));
						partitionDetails.put("leader", partitionInfo.leader().id());
						return partitionDetails;
					}));

			topicInfo.put("partitions", partitions);

		} catch (Exception e) {
//			logger.error("Something wents wrong while describing topic");
			e.getStackTrace();
		}
		return topicInfo;
	}

	public Map<String, Object> describeTopicConfig(String topicName, long startTime) {
		try {
//			logger.info(">> Describing topic config with topic name : {}", topicName);

			AdminClient adminClient = AdminClient.create(getAdminConfig());
			ConfigResource resource = new ConfigResource(Type.TOPIC, topicName);
			DescribeConfigsResult describeConfigsResult = adminClient
					.describeConfigs(Collections.singletonList(resource));
			Config config = describeConfigsResult.all().get().get(resource);

			ListTopicsResult listTopicsResult = adminClient.listTopics();
			KafkaFuture<Set<String>> topicsFuture = listTopicsResult.names();
			Set<String> topics = topicsFuture.get();

			Map<String, Object> configEntries = new HashMap<>();
			for (ConfigEntry entry : config.entries()) {
				configEntries.put(entry.name(), entry.value());
			}
			configEntries.put("topicResult", topics);
			return configEntries;
		} catch (Exception e) {
//			logger.error("Something wents wrong while describing topic");
			e.getStackTrace();
		}
		return null;
	}

	private Properties getAdminConfig() {
		Properties configs = new Properties();
		try {
//			logger.info(">> preparing admin config ");

			configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
			configs.put("sasl.jaas.config", jaasConfig);
			configs.put("security.protocol", securityProtocol);
			configs.put("sasl.mechanism", saslMechanism);

		} catch (Exception e) {
//			logger.error("Something wents wrong while preparing admin config");
		}
		return configs;
	}
}
