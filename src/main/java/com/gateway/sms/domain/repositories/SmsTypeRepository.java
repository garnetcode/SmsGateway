package com.gateway.sms.domain.repositories;

import com.gateway.sms.domain.enums.sms.Provider;
import com.gateway.sms.models.SmsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SmsTypeRepository extends JpaRepository<SmsType, UUID> {

    SmsType findByProvider(Provider provider);
}
