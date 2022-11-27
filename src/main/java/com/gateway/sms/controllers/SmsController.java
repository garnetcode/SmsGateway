package com.gateway.sms.controllers;
import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.response.Response;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.services.sms.implementations.SmsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@RestController
@RequestMapping(path = "api/v1/sms")
@AllArgsConstructor
@Slf4j
public class SmsController {
    private final Response response;
    private final SmsServiceImpl smsService;

    @PostMapping
    @RequestMapping(path = "/send")
    public @ResponseBody Response send(@RequestBody @Valid SmsDto smsDto) {
        log.info(smsDto.toString());
        log.info("LOGGED IN : "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        response.setSuccess(true);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Successful!!");
        response.setData(smsService.sendSmsJSON(smsDto));
        return response;
    }
}
