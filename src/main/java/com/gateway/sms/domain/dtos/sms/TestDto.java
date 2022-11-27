package com.gateway.sms.domain.dtos.sms;

import lombok.Data;

import java.io.Serializable;

@Data
public class TestDto implements Serializable {

    private int userId;
    private int id;
    private String title;
    private String body;

    // getters and setters
}