package com.gateway.sms.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;


@Entity @Getter @Setter
public class Role extends BaseEntity{
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }


}
