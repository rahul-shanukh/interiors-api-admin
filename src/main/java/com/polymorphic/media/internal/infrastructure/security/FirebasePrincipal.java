package com.polymorphic.media.internal.infrastructure.security;

import java.util.Map;
import java.util.Set;

/**
 * Application-level representation of an authenticated Firebase user.
 */
public record FirebasePrincipal(
        String uid,
        String email,
        boolean emailVerified,
        Set<String> roles,
        Map<String, Object> claims
) {
}

