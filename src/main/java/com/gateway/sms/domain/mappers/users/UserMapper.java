package com.gateway.sms.domain.mappers.users;

import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.models.AppUser;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    AppUser userPostDtoToUser(PostUserDto userPostDto);
}


