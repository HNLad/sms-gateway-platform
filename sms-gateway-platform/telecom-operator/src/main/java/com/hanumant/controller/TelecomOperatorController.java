package com.hanumant.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanumant.model.SmsResponse;

@RestController
@RequestMapping("/telco")
public class TelecomOperatorController {

	@GetMapping("/smsc")
	public ResponseEntity<SmsResponse> operate(@RequestParam(name = "account_id") Integer accountId,
			@RequestParam(name = "mobile") Integer mobile, @RequestParam(name = "message") String message) {
		
		String ack_ID = UUID.randomUUID().toString();
		
		if (mobile == null || message == null || message.length() > 160) {
			return ResponseEntity.ok(new SmsResponse("REJECTED", "FAILUIRE", null));
		}

		return ResponseEntity.ok(new SmsResponse("ACCEPTED", "SUCCESS", ack_ID));

	}

}
