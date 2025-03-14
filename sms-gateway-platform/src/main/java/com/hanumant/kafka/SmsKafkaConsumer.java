package com.hanumant.kafka;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanumant.config.SingleObjMapper;
import com.hanumant.model.SendMessage;
import com.hanumant.repo.SendMessageRepository;

@Service
public class SmsKafkaConsumer {
    
    private static final Logger log = LoggerFactory.getLogger(SmsKafkaConsumer.class);

    private final SendMessageRepository sendMessageRepository;
    
	public SmsKafkaConsumer(SendMessageRepository sendMessageRepo) {
		this.sendMessageRepository = sendMessageRepo;
	}
    
    @KafkaListener(topics = "sms_topic", groupId = "sms_group")
    public void consume(String message) {
    	log.info("Entering SmsKafkaConsumer :: consume with message -> "+message);
    	try {
    		    ObjectMapper mapper = SingleObjMapper.INSTANCE.getObjectMapper();
    	        JsonNode jsonNode = mapper.readTree(message);
    	        
    	        SendMessage smsMessage = new SendMessage();
    	        smsMessage.setMobile(jsonNode.get("mobile").asInt());
    	        smsMessage.setMessage(jsonNode.get("message").asText());
    	        smsMessage.setAccountId(jsonNode.get("account_id").asInt());
    	        smsMessage.setStatus("NEW");
    	        smsMessage.setReceivedTs(new Timestamp(System.currentTimeMillis()));
    	        
    	        SendMessage save = sendMessageRepository.save(smsMessage);
    	        log.info("Result for saving the message to the table : "+save);
    	}catch (Exception e) {
    		log.error(e.getMessage());
			e.printStackTrace();
		}
      
    }
}

