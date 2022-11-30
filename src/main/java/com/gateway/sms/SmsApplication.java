package com.gateway.sms;

import com.gateway.sms.domain.dtos.sms.SmsTypeDto;
import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.domain.enums.sms.SenderId;
import com.gateway.sms.domain.repositories.RoleRepository;
import com.gateway.sms.domain.repositories.SmsTypeRepository;
import com.gateway.sms.models.Role;
import com.gateway.sms.models.SmsType;
import com.gateway.sms.services.users.implementations.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication @Slf4j
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }


    @Bean
    CommandLineRunner run(AppUserService userService, RoleRepository roleRepository, SmsTypeRepository smsTypeRepository){
        return args -> {

            Optional<Role> user_role = Optional.ofNullable(roleRepository.findFirstByName("ROLE_USER"));
            if(user_role.isEmpty()){
                userService.saveRole(new Role("ROLE_USER"));
            }

            Optional<Role> admin_role = Optional.ofNullable(roleRepository.findFirstByName("ROLE_ADMIN"));
            if(admin_role.isEmpty()){
                userService.saveRole(new Role("ROLE_ADMIN"));
            }

            Optional<SmsType> smsType = Optional.ofNullable(smsTypeRepository.findByProvider(Provider.valueOf("ECONET")));
            if(smsType.isEmpty()){
                SmsType smsType1 = new SmsType();
                smsType1.setCostPer160Characters(3.00);
                smsType1.setProvider(Provider.valueOf("ECONET"));
                smsType1.setSenderId(SenderId.valueOf("IAS"));
                smsTypeRepository.save(smsType1);
            }


        };
    }

}
