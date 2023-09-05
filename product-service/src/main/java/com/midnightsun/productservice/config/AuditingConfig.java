package com.midnightsun.productservice.config;

import com.midnightsun.productservice.config.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditingConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(SecurityUtils.getCurrentLoggedUser()).orElse(Optional.of("system"));
    }
}
