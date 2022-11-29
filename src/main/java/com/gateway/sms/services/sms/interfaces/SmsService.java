package com.gateway.sms.services.sms.interfaces;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.dtos.sms.SmsTypeDto;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.domain.response.sms.SmsResponse;
import com.gateway.sms.models.AppUser;
import org.springframework.http.ResponseEntity;

public interface SmsService {

    ApiResponse sendSms(AppUser appUser, SmsDto sms);
    ApiResponse retrieve(String phoneNumber);

    ApiResponse addProvider(SmsTypeDto smsTypeDto);
}
