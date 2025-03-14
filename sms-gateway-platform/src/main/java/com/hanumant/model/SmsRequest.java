package com.hanumant.model;

import lombok.Data;

@Data
public class SmsRequest {
    private String username;
    private String password;
    private Integer mobile;

    private String message;
}
