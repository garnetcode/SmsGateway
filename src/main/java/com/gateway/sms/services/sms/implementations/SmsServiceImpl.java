package com.gateway.sms.services.sms.implementations;

import com.gateway.sms.domain.dtos.sms.SmsDto;
import com.gateway.sms.domain.dtos.sms.SmsTypeDto;
import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.domain.repositories.CompanyRepository;
import com.gateway.sms.domain.repositories.SmsRepository;
import com.gateway.sms.domain.repositories.SmsTypeRepository;
import com.gateway.sms.domain.response.ApiResponse;
import com.gateway.sms.domain.response.sms.SmsResponse;
import com.gateway.sms.models.AppUser;
import com.gateway.sms.models.Company;
import com.gateway.sms.models.Sms;
import com.gateway.sms.models.SmsType;
import com.gateway.sms.security.config.ServerConfig;
import com.gateway.sms.services.sms.implementations.mappers.sms.SmsMapper;
import com.gateway.sms.services.sms.implementations.mappers.sms.SmsTypeMapper;
import com.gateway.sms.services.sms.implementations.mappers.users.CompanyMapper;
import com.gateway.sms.services.sms.interfaces.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service @Slf4j
public class SmsServiceImpl implements SmsService {

    private final RestTemplate restTemplate;
    private final ServerConfig config;

    private final SmsRepository smsRepository;

    private final SmsTypeRepository smsTypeRepository;

    private final SmsMapper smsMapper;

    private final SmsTypeMapper smsTypeMapper;

    private final ApiResponse response;

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;



    public SmsServiceImpl(RestTemplateBuilder restTemplateBuilder,
                          ServerConfig config,
                          SmsRepository smsRepository,
                          SmsTypeRepository smsTypeRepository,
                          SmsMapper smsMapper,
                          SmsTypeMapper smsTypeMapper,
                          ApiResponse response,
                          CompanyRepository companyRepository,
                          CompanyMapper companyMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.config = config;
        this.smsRepository = smsRepository;
        this.smsTypeRepository = smsTypeRepository;
        this.companyRepository = companyRepository;
        this.response = response;
        this.smsMapper = smsMapper;
        this.smsTypeMapper = smsTypeMapper;
        this.companyMapper = companyMapper;
    }

    public ApiResponse sendSms(AppUser appUser, SmsDto smsDto)  {
        Company company = companyRepository.findByAdmin(appUser);

        SmsType smsType = smsTypeRepository.findByProvider(smsDto.getProvider());
        double cost = smsType.getCostPer160Characters() *Math.ceil((double) smsDto.getMessage().length()/160);
        smsDto.setMessageCost(cost);
        smsDto.setCompany(company);

        if(!company.getIsActive()){
            response.failed();
            response.setMessage("Your Profile Is Inactive. Please Contact Administrator!");
            response.setStatus(HttpStatus.LOCKED);
            return response;
        }

        if(company.getRunningBalance() < cost ){
            response.failed();
            response.setMessage("You Have Insufficient Balance!");
            response.setStatus(HttpStatus.PAYMENT_REQUIRED);
            return response;
        }

        String apiUrl = String.format("https://mobilemessaging.econet.co.zw/client/api/sendmessage?apikey=%s&mobiles=%s&sms=%s&senderid=IAS",
                config.getApiKey(),smsDto.getPhoneNumber(),
                URLEncoder.encode(smsDto.getMessage(),
                        StandardCharsets.UTF_8)) ;
        try{
            ResponseEntity <SmsResponse> smsResponse = restTemplate.getForEntity(apiUrl, SmsResponse.class);

            log.info(String.valueOf(smsResponse));
            HttpStatus statusCode = smsResponse.getStatusCode();
            log.info(String.valueOf(statusCode));
            if(statusCode.equals(HttpStatus.OK)){
                Optional<SmsType> provider = Optional.ofNullable(smsTypeRepository.findByProvider(Provider.valueOf(smsDto.getProvider().name())));
                return provider.map(value->{

                    //Update message
                    smsDto.setMessageCost(cost);
                    smsDto.setSent(true);
                    smsDto.setCompany(company);
                    log.info("=========>>> ENTRY");
                    smsDto.setSenderId(value.getSenderId().toString());
                    Sms sms = smsMapper.smsDtoSms(smsDto);

                    company.setRunningBalance(company.getRunningBalance()-cost);

                    companyMapper.updateCompanyFromCompanyDto(companyMapper.companyToCompanyDto(company), company);

                    //Deduct Balance
                    smsDto.setCompany(company);

                    smsRepository.save(sms);


                    //Constructing ApiResponse
                    response.setSuccess(true);
                    response.setStatus(HttpStatus.OK);
                    response.setMessage("Success");
                    response.setData(smsDto);
                    return response;
                }).orElseGet(()->{
                    response.setSuccess(false);
                    response.setStatus(HttpStatus.BAD_REQUEST);
                    response.setMessage("Provider does not exist");
                    response.setData(null);
                    return response;
                });
            }
            log.info(response.toString());
            return response;
        }catch (HttpStatusCodeException e){
            response.failed();
            response.setMessage("An Error Occurred, Please Contact Administrator");
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setData(e.getMessage());
        }

        return null;
    }

    @Override
    public ApiResponse retrieve(String phoneNumber) {
        return null;
    }

    @Override
    public ApiResponse addProvider(SmsTypeDto smsTypeDto) {
        Optional<SmsType> provider = Optional.ofNullable(smsTypeRepository.findByProvider(smsTypeDto.getProvider()));
        return provider.map(value->{
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage("Provider already exists");
            response.setData(null);
            return response;
        }).orElseGet(()->{
            SmsType newProvider = smsTypeMapper.smsTypeDtoSmsType(smsTypeDto);
            smsTypeRepository.save(newProvider);

            //Constructing ApiResponse
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Success");
            response.setData(smsTypeMapper.smsTypeToSmsTypeDto(newProvider));
            return response;
        });
    }
}