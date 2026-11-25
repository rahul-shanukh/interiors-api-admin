package com.interiors.interiorsadminapi.security.firebase;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Externalized Firebase security settings. */
@ConfigurationProperties(prefix = "firebase")
public class FirebaseProperties {

    private String projectId;
    private String credentialsLocation;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCredentialsLocation() {
        return credentialsLocation;
    }

    public void setCredentialsLocation(String credentialsLocation) {
        this.credentialsLocation = credentialsLocation;
    }
}

