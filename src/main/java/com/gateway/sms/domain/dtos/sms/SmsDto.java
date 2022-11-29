package com.gateway.sms.domain.dtos.sms;

import com.gateway.sms.models.Company;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SmsDto {
    @NotEmpty(message = "Phone number is required!")
    private String phoneNumber;

    @NotNull(message = "Message is required!")
    private String message;

    @NotNull(message = "Provider is required i.e. (ECONET, NETONE or TELECEL)")
    private String provider;

    private String senderId;

    private Double messageCost;

    private Boolean sent;

    private Company company;
}
