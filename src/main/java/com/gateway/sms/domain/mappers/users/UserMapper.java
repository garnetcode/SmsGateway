package com.gateway.sms.domain.mappers.users;

import com.gateway.sms.domain.dtos.users.PostUserDto;
import com.gateway.sms.models.AppUser;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface UserMapper {

    AppUser userDtoToUser(PostUserDto userDto);

    AppUser userToUserDto(AppUser user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AppUser updateUserFromUserDto(PostUserDto userDto, @MappingTarget AppUser user);
}


