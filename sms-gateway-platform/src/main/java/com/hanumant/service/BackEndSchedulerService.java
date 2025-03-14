package com.hanumant.service;

import com.hanumant.model.SendMessage;
import com.hanumant.model.SmsResponse;
import com.hanumant.repo.SendMessageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BackEndSchedulerService {

	private static final Logger log = LoggerFactory.getLogger(BackEndSchedulerService.class);

	private final SendMessageRepository sendMessageRepository;

	private final RestTemplate restTemplate;
	
	public BackEndSchedulerService(SendMessageRepository sendMessageRepository, RestTemplate restTemplate ) {
		this.sendMessageRepository = sendMessageRepository;
		this.restTemplate = restTemplate;
	}

	//@Scheduled(fixedRate = 1000) //commented for development purpose only
	public void processSmsMessages() {
		log.info("Inside BackEndSchedulerService::processSmsMessages scheduled for every 1 sec");
		try {
			List<SendMessage> newMessages = sendMessageRepository.findByStatus("NEW");

			log.info("messages found with status new : " + newMessages);

			for (SendMessage message : newMessages) {
				log.info("Processing new messages and upating the status to INPROCESS");
				message.setStatus("INPROCESS");
				log.info("Saving in db with new status INPROCESS");

				sendMessageRepository.save(message);

				log.info("Sending a call to the telecom operator service for message : " + message);
				String telecomUrl = String.format("http://localhost:8080/telco/smsc?account_id=%d&mobile=%d&message=%s",
						message.getAccountId(), message.getMobile(),
						URLEncoder.encode(message.getMessage(), StandardCharsets.UTF_8));

				ResponseEntity<SmsResponse> response = restTemplate.getForEntity(telecomUrl, SmsResponse.class);
				SmsResponse smsResponse = response.getBody();

				if (smsResponse.getResponseCode().equals("SUCCESS")) {
					log.info("Message -> " + message + " ,updated to status SENT ");
					message.setStatus("SENT");
					message.setTelcoResponse(smsResponse.getStatus());
					message.setSentTs(new Timestamp(System.currentTimeMillis()));
					sendMessageRepository.save(message);
				}
				log.info("Exiting processSmsMessages with response : " + smsResponse);
			}

		} catch (Exception e) {
			log.error("Error occured while sending a call to Telecome operator service.");
			log.error(e.getMessage());
		}
	}
}
