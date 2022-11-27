package com.gateway.sms.domain.response.sms;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    @JsonAlias(value = {"sms-client-id"})
    private int smsClientId;

    @JsonAlias(value = {"message-id"})
    private int messageId;

    @JsonAlias(value = {"mobile-no"})
    private String mobileNumber;

}