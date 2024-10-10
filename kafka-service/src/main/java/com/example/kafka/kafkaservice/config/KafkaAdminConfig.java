package com.example.kafka.kafkaservice.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;

@EnableKafka
@Configuration
public class KafkaAdminConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.properties.sasl.jaas.config}")
	private String jaasConfig;

	@Value("${spring.kafka.properties.security.protocol}")
	private String securityProtocol;

	@Value("${spring.kafka.properties.sasl.mechanism}")
	private String saslMechanism;

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put("sasl.jaas.config", jaasConfig);
		props.put("security.protocol", securityProtocol);
		props.put("sasl.mechanism", saslMechanism);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configs.put("sasl.jaas.config", jaasConfig);
		configs.put("security.protocol", securityProtocol);
		configs.put("sasl.mechanism", saslMechanism);
		return new KafkaAdmin(configs);
	}

	@Bean
	public AdminClient adminClient() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configs.put("sasl.jaas.config", jaasConfig);
		configs.put("security.protocol", securityProtocol);
		configs.put("sasl.mechanism", saslMechanism);
		return AdminClient.create(configs);
	}

}
