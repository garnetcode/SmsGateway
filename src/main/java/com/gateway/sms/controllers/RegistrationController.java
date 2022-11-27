package com.gateway.sms.controllers;

import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.response.Response;
import com.gateway.sms.services.users.implementations.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController @Slf4j
@RequestMapping(path = "api/v1/user/registration")
@AllArgsConstructor
public class RegistrationController {
    private AppUserService registrationService;





    @PostMapping
    public @ResponseBody Response register(@RequestBody @Valid PostUserDto appUser) {
        return registrationService.signUpUser(appUser);

    }
}
