package com.polymorphic.media.internal.infrastructure.security;

/**
 * Boundary used by authentication infrastructure to verify Firebase ID tokens.
 */
public interface FirebaseTokenVerifier {

    FirebasePrincipal verify(String idToken);
}

