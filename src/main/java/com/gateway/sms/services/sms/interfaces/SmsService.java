package com.gateway.sms.services.sms.interfaces;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.response.Response;

public interface SmsService {

    Response sendSms(SmsDto sms);
    Response retrieve(String phoneNumber);
}
