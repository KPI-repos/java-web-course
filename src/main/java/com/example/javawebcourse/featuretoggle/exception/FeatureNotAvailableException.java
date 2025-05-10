package com.example.javawebcourse.featuretoggle.exception;

public class FeatureNotAvailableException extends RuntimeException {
    private static final String FEATURE_TOGGLE_NOT_ENABLED = "Feature toggle %s is not enabled";

    public FeatureNotAvailableException(String featureToggleName) {
        super(String.format(FEATURE_TOGGLE_NOT_ENABLED, featureToggleName));
    }
}