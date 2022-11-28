package com.gateway.sms.domain.repositories;

import com.gateway.sms.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository  extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
