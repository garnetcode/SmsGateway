package com.gateway.sms.services.sms.implementations.mappers.sms;

import com.gateway.sms.domain.dtos.sms.SmsTypeDto;
import com.gateway.sms.models.SmsType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface SmsTypeMapper {

    SmsType smsTypeDtoSmsType(SmsTypeDto smsTypeDto);

    SmsTypeDto smsTypeToSmsTypeDto(SmsType smsType);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SmsType updateSmsTypeFromSmsTypeDto(SmsTypeDto smsTypeDto, @MappingTarget SmsType smsType);
}

