package com.hanumant.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ConfigModel(

		String kafkaBootstrapServers,
		String kafkaConsumerGroupId
) {}
