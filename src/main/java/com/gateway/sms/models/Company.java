package com.gateway.sms.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Objects;

@Setter @Getter
@RequiredArgsConstructor @Entity
public class Company extends BaseEntity{

    @OneToOne
    private AppUser admin;

    private Boolean isActive;

    @Column(name = "name")
    private String companyName;

    @Column(name = "balance")
    private Double runningBalance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Company company = (Company) o;
        return getId() != null && Objects.equals(getId(), company.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
