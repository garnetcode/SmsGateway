package com.gateway.sms.models;

import com.gateway.sms.domain.enums.sms.SenderId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter @Getter @Entity
@NoArgsConstructor
@AllArgsConstructor @Table(name = "Sms")
public class Sms extends BaseEntity{

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "message")
    private String message;

    @Enumerated(value = EnumType.STRING)
    private SenderId smsSenderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser sender;


}
