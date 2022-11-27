package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collection;
//import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class SmsDetails implements Serializable{

    @JsonAlias(value = {"success-count"})
    @JsonProperty(namespace = "success-count")
    private Integer successCount;

    @JsonAlias(value = {"failed-sms-details"})
    @JsonProperty(namespace = "failed-sms-details")
    private String failedSmsDetail;

//    @JsonAlias(value = {"sent-sms-details"})
    @JsonProperty(namespace = "sent-sms-details")
    private Collection<Message> sentSmsDetails;
}
