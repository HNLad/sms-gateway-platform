package com.hanumant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hanumant.config.ConfigModel;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(value = ConfigModel.class)
public class SmsPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmsPlatformApplication.class, args);
	}
}
