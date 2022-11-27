package com.gateway.sms.controllers;
import com.gateway.sms.domain.response.Response;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.services.sms.implementations.SmsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(path = "api/v1/sms")
@AllArgsConstructor
@Slf4j
public class SmsController {
    private final Response response;
    private final SmsServiceImpl smsService;

    @GetMapping
    @RequestMapping(path = "/send")
    public @ResponseBody Response send(){
        log.info("LOGGED IN : "+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("============>>>> "+ Arrays.toString(smsService.getPostsPlainJSON()));
        response.setSuccess(true);
        response.setMessage("Successful!!");
        response.setData("fetched");
        return response;
    }
}
