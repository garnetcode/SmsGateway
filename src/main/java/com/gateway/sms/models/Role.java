package com.gateway.sms.models;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import java.util.Objects;


@Entity @Getter @Setter
public class Role extends BaseEntity{
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }


}
