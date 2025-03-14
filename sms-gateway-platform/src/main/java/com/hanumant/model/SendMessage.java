package com.hanumant.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "send_msg_tracker")
@Data
public class SendMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer mobile;
    private String message;
    private String status;
    
    @Column(name = "received_ts")
    private Timestamp receivedTs;
    
    @Column(name = "sent_ts")
    private Timestamp sentTs;
    
    @Column(name = "account_id")
    private Integer accountId;
    
    @Column(name = "telco_response")
    private String telcoResponse;
}
