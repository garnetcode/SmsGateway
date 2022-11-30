package com.gateway.sms.controllers;

import com.gateway.sms.domain.dtos.users.CompanyDto;
import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.repositories.AppUserRepository;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.services.users.implementations.AppUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController @Slf4j
@RequestMapping(path = "api/v1/user/")
@AllArgsConstructor
public class RegistrationController {
    private AppUserService registrationService;

    private AppUserRepository appUserRepository;



    @PostMapping
    public @ResponseBody ApiResponse register(@RequestBody @Valid PostUserDto appUser) {
        return registrationService.signUpUser(appUser);
    }

    @PostMapping
    @RequestMapping(path = "create/company/")
    public @ResponseBody ApiResponse createCompany(@RequestBody @Valid CompanyDto companyDto){
        String admin = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findFirstByUsername(admin);
        return registrationService.createCompany(appUser, companyDto);
    }

    @GetMapping
    @RequestMapping(path = "fetch/company/")
    public @ResponseBody ApiResponse getCompanyProfile(){
        String admin = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findFirstByUsername(admin);
        return registrationService.getCompanyProfile(appUser);
    }

    @GetMapping
    @RequestMapping(path = "update/company/")
    public @ResponseBody ApiResponse updateCompanyProfile(@RequestParam(name = "name") String name, @RequestParam Boolean isActive){

        return registrationService.updateCompanyProfile(name, isActive);
    }
}
