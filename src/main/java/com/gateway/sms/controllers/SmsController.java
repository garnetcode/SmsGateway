package com.gateway.sms.controllers;
import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.repositories.AppUserRepository;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.services.sms.implementations.SmsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/sms")
@AllArgsConstructor
@Slf4j
public class SmsController {
//    private final ApiResponse response;
    private final SmsServiceImpl smsService;

    private AppUserRepository appUserRepository;

    @PostMapping
    @RequestMapping(path = "/send")
    public @ResponseBody ApiResponse send(@RequestBody @Valid SmsDto smsDto) {
        String admin = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByUsername(admin);
        return smsService.sendSms(appUser, smsDto);
    }
}
