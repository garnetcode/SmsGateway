package com.gateway.sms.domain.mappers.users;

import com.gateway.sms.domain.dtos.users.CompanyDto;
import com.gateway.sms.models.Company;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface CompanyMapper {

    Company companyDtoToCompany(CompanyDto companyDto);

    CompanyDto companyToCompanyDto(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company updateCompanyFromCompanyDto(CompanyDto companyDto, @MappingTarget Company company);
}
