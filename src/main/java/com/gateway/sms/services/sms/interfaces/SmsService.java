package com.gateway.sms.services.sms.interfaces;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.response.ApiResponse;

public interface SmsService {

    ApiResponse sendSms(SmsDto sms);
    ApiResponse retrieve(String phoneNumber);
}
