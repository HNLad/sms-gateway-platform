package com.hanumant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponse {
    private String status;
    private String responseCode;
    private String acknowledgementId;
}
