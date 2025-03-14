package com.hanumant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanumant.config.SingleObjMapper;
import com.hanumant.model.SmsResponse;
import com.hanumant.model.User;
import com.hanumant.repo.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/telco")
public class CustomerApiController {

	private static final Logger log = LoggerFactory.getLogger(CustomerApiController.class);

	private final UserRepository userRepository;

	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public CustomerApiController(UserRepository userRepo, KafkaTemplate<String, String> kafkaTempt) {
		this.userRepository = userRepo;
		this.kafkaTemplate = kafkaTempt;
	}

	@GetMapping("/sendmsg")
	public ResponseEntity<SmsResponse> sendMessage(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, @RequestParam(name = "mobile") Integer mobile,
			@RequestParam(name = "message") String message) throws JsonProcessingException {

		log.info("Entering CustomerApiController::sendMessage for username : " + username + " password: " + password
				+ " mobile : " + mobile + " message : " + message);

		String ackId = null;
		try {
			ObjectMapper mapper = SingleObjMapper.INSTANCE.getObjectMapper();

			ackId = UUID.randomUUID().toString();
			User user = userRepository.findByUsernameAndPassword(username, password);

			if (user == null) {
				log.info("Unauthorized user, not found in database!");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new SmsResponse("REJECTED", "FAILURE", null));
			}

			if (mobile == null || message == null || message.length() > 160) {
				log.info("Validation failed mobile is null or message length is more than 160 characters");
				return ResponseEntity.badRequest().body(new SmsResponse("REJECTED", "FAILURE", null));
			}

			Map<String, Object> kafkaMessage = new HashMap<>();
			kafkaMessage.put("ack_id", ackId);
			kafkaMessage.put("account_id", user.getAccountId());
			kafkaMessage.put("mobile", mobile);
			kafkaMessage.put("message", message);

			log.info("Producing the data to kafka topic : sms_topic");
			kafkaTemplate.send("sms_topic", mapper.writeValueAsString(kafkaMessage));

			return ResponseEntity.ok(new SmsResponse("ACCEPTED", "SUCCESS", ackId));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ResponseEntity.badRequest().body(new SmsResponse("REJECTED", "FAILURE", null));
	}
}
