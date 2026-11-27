package com.polymorphic.media.internal.infrastructure.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Externalized Firebase security settings.
 */
@Validated
@ConfigurationProperties(prefix = "firebase")
public record FirebaseProperties (

        String projectId,
        String credentialsLocation
){}

