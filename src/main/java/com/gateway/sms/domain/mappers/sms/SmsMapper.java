package com.gateway.sms.domain.mappers.sms;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.models.Sms;

public interface SmsMapper {
    Sms smsDtoSms(SmsDto smsDto);
}
