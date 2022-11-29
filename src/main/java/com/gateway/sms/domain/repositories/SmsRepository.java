package com.gateway.sms.domain.repositories;

import com.gateway.sms.models.Sms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SmsRepository extends JpaRepository<Sms, UUID> {
}
