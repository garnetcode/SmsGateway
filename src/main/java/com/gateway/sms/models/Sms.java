package com.gateway.sms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter @Getter @Entity
@NoArgsConstructor
@AllArgsConstructor
public class Sms extends BaseEntity{

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "senderId")
    private String senderId;

    @Column(name = "sender")
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser sender;


}
