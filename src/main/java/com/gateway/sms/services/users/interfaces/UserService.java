package com.gateway.sms.services.users.interfaces;

import com.gateway.sms.domain.dtos.users.CompanyDto;
import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.models.AppUser;

public interface UserService {

    ApiResponse signUpUser(PostUserDto appUser);

    void addRoleToUser(String username, String roleName);

    ApiResponse createCompany(AppUser admin, CompanyDto companyDto);
}
