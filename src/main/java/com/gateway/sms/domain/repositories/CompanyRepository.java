package com.gateway.sms.domain.repositories;

import com.gateway.sms.models.AppUser;
import com.gateway.sms.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Company findByAdmin(AppUser appUser);
}
