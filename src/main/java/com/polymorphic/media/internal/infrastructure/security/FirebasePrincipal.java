package com.polymorphic.media.internal.infrastructure.security;

import java.util.Map;
import java.util.Objects;
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
    public FirebasePrincipal{
        Objects.requireNonNull(uid, "uid is required");

        if (uid.isBlank()) {
            throw new IllegalArgumentException("Firebase UID cannot be blank");
        }

        roles = Set.copyOf(Objects.requireNonNull(roles, "roles is required"));
        claims  = Map.copyOf(Objects.requireNonNull(claims, "claims is required"));
    }
}

