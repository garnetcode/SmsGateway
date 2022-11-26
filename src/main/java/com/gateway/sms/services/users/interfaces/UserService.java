package com.gateway.sms.services.users.interfaces;

import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.domain.response.Response;

public interface UserService {

    Response signUpUser(PostUserDto appUser);

    void addRoleToUser(String username, String roleName);
}
