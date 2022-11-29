package com.gateway.sms.models;

import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.domain.enums.sms.SenderId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Setter @Getter @NoArgsConstructor
@AllArgsConstructor @Entity
@Table(name = "SmsTypes")
public class SmsType extends BaseEntity {
    private Double costPer160Characters;

    @Enumerated(value = EnumType.STRING)
    private Provider provider;

    @Enumerated(value = EnumType.STRING)
    private SenderId senderId;

}
