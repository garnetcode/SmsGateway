package com.gateway.sms.models;

import com.gateway.sms.domain.enums.sms.Provider;
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

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    private Double messageCost;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company sender;

    private Boolean sent;

//    public void setMessageCost(Double messageCost) {
//        Double messageCount = (double) (this.message.length() / 160)*2;
//        this.messageCost = messageCost;
//    }
}
