package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor
public class SmsDetails{

    @JsonAlias(value = {"success-count"})
    @JsonProperty(namespace = "success-count")
    private String successCount;

    @JsonAlias(value = {"failed-sms-details"})
    private List<String> failedSmsDetail;

    @JsonAlias(value = {"sent-sms-details"})
    private List<Message> sentSmsDetail;
}
