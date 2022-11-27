package com.gateway.sms.services.sms.implementations;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.response.sms.SmsResponse;
import com.gateway.sms.security.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

    public SmsResponse sendSmsJSON(SmsDto smsDto)  {
        String apiUrl = String.format("https://mobilemessaging.econet.co.zw/client/api/sendmessage?apikey=%s&mobiles=%s&sms=%s&senderid=%s", config.getApiKey(),smsDto.getPhoneNumber(), URLEncoder.encode(smsDto.getMessage(), StandardCharsets.UTF_8), smsDto.getSenderId()) ;
        try{
            SmsResponse response = restTemplate.getForEntity(apiUrl, SmsResponse.class).getBody();
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.readValue(response, SmsResponse.class)
//            HttpStatus statusCode = response.getStatusCode();
//            log.info(statusCode + "  "+response.toString());
//            if(statusCode.equals(HttpStatus.OK)){

//            }
            assert response != null;
            log.info(response.toString());
            return response;
        }catch (HttpStatusCodeException e){
            log.info(e.getResponseBodyAsString());
            log.error(e.getMessage());
        }


        return null;
    }
}