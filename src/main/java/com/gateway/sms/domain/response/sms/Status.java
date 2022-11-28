package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Status {

    @JsonAlias({"error-code"})
    private String errorCode;

    @JsonAlias({"error-status"})
    private String errorStatus;

    @JsonAlias({"error-description"})
    private String errorDescription;
}
