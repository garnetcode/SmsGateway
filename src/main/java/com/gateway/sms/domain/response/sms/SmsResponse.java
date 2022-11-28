package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import java.util.List;

@Getter @AllArgsConstructor
@Setter @NoArgsConstructor
public class SmsResponse {
    private Status status;
    private String statusCode;
    private Integer statusCodeValue;

    @JsonAlias(value = {"sms-response-details"})
    private List<SmsDetails> details;


}
