package com.interiors.interiorsadminapi.security.firebase;

/** Boundary used by authentication infrastructure to verify Firebase ID tokens. */
public interface FirebaseTokenVerifier {

    FirebasePrincipal verify(String idToken);
}

