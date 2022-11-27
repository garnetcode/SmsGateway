package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter @AllArgsConstructor
@Setter @NoArgsConstructor
public class SmsResponse implements Serializable {
    private Object status;
    private String statusCode;
    private Integer statusCodeValue;

    @JsonAlias({"sms-response-details"})
    private SmsDetails details;


}
