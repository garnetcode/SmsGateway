package com.gateway.sms.domain.dtos.sms;

import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.domain.enums.sms.SenderId;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class SmsTypeDto {
    @NotNull(message = "Cost is a required field")
    private Double costPer160Characters;

    @NotNull(message = "Provider is a required field")
    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @NotNull(message = "SenderId is a required field")
    @Enumerated(value = EnumType.STRING)
    private SenderId senderId;




}
