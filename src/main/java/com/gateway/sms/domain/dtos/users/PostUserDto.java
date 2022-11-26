package com.gateway.sms.domain.dtos.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public record PostUserDto(@NotBlank(message = "Name cannot be null") @NotNull(message = "Name cannot be null") String name,
                          @NotBlank @NotNull(message = "Username cannot be null") String username,
                          @NotBlank @NotNull(message = "Password cannot be null") String password) {

}
