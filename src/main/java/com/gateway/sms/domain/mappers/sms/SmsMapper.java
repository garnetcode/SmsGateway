package com.gateway.sms.domain.mappers.sms;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.models.Sms;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface SmsMapper {
    Sms smsDtoSms(SmsDto smsDto);
}
