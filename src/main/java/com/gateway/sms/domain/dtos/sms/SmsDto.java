package com.gateway.sms.domain.dtos.sms;

import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.models.Company;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SmsDto {

    @NotEmpty(message = "Phone number is required!")
    @Pattern(regexp = "^(\\+?263|0)([789])\\d{8}$")
    private String phoneNumber;

    @NotNull(message = "Message is required!")
    private String message;

    @NotNull(message = "Provider is required i.e. (ECONET, NETONE or TELECEL)")
    @Enumerated(value = EnumType.STRING)
    private Provider provider = Provider.ECONET;

    private String senderId = "IAS";

    private Double messageCost;

    private Boolean sent = false;

    private Company company;
}
