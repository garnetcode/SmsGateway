package com.gateway.sms.domain.dtos.sms;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SmsDto {
    @NotEmpty(message = "Phone number is required!")
    private String phoneNumber;

    @NotNull(message = "Message is required!")
    private String message;

    @NotNull(message = "SenderId not specified!")
    private String senderId;
}
