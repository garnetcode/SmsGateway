package com.gateway.sms.services.sms.implementations.mappers.sms;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.models.Sms;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface SmsMapper {
    Sms smsDtoSms(SmsDto smsDto);

    SmsDto smsToSmsDto(Sms sms);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sms updateSmsFromSmsDto(SmsDto smsDto, @MappingTarget Sms sms);
}
