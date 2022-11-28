package com.gateway.sms.domain.dtos.users;

import com.gateway.sms.models.AppUser;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CompanyDto {
    private AppUser admin;
    private Boolean isActive;


    private Double runningBalance;

    @NotNull(message = "Company name is required!")
    private String companyName;
}
