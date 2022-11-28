package com.gateway.sms.services.sms.implementations;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.response.sms.SmsResponse;
import com.gateway.sms.security.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service @Slf4j
public class SmsServiceImpl {

    private final RestTemplate restTemplate;
    private final ServerConfig config;

    public SmsServiceImpl(RestTemplateBuilder restTemplateBuilder, ServerConfig config) {
        this.restTemplate = restTemplateBuilder.build();
        this.config = config;

    }

    public ResponseEntity<SmsResponse> sendSms(SmsDto smsDto)  {
        String apiUrl = String.format("https://mobilemessaging.econet.co.zw/client/api/sendmessage?apikey=%s&mobiles=%s&sms=%s&senderid=%s",
                config.getApiKey(),smsDto.getPhoneNumber(),
                URLEncoder.encode(smsDto.getMessage(),
                        StandardCharsets.UTF_8),
                smsDto.getSenderId()) ;
        try{
            ResponseEntity <SmsResponse> response = restTemplate.getForEntity(apiUrl, SmsResponse.class);
            HttpStatus statusCode = response.getStatusCode();
            if(statusCode.equals(HttpStatus.OK)){
                //TO-DO
                // Implementations
                log.info("RESPONSE : "+ response.getBody());
            }
            log.info(response.toString());
            return response;
        }catch (HttpStatusCodeException e){
            log.info(e.getResponseBodyAsString());
            log.error(e.getMessage());
        }


        return null;
    }
}