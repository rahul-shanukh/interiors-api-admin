package com.polymorphic.media.internal.infrastructure.security;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Global Firebase authentication configuration for the application.
 *
 * Concrete Firebase initialization and bean wiring should be added only here,
 * so all application modules share one Firebase integration point.
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FirebaseProperties.class)
public class FirebaseSecurityConfiguration {

    // FirebaseApp and FirebaseAuth bean definitions will be added after implementation.
}

