package com.example.javawebcourse.api.featuretoggle;

import com.example.javawebcourse.api.annotation.DisabledFeatureToggle;
import com.example.javawebcourse.api.annotation.EnabledFeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggleService;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FeatureToggleExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> {
            FeatureToggleService featureToggleService = getFeatureToggleService(context);

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle annotation = method.getAnnotation(EnabledFeatureToggle.class);
                String featureName = annotation.value().getFeatureName();
                featureToggleService.enable(featureName);
            }

            if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle annotation = method.getAnnotation(DisabledFeatureToggle.class);
                String featureName = annotation.value().getFeatureName();
                featureToggleService.disable(featureName);
            }
        });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        context.getTestMethod().ifPresent(method -> {
            String featureName = null;

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle annotation = method.getAnnotation(EnabledFeatureToggle.class);
                featureName = annotation.value().getFeatureName();
            }

            if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle annotation = method.getAnnotation(DisabledFeatureToggle.class);
                featureName = annotation.value().getFeatureName();
            }

            if (featureName != null) {
                FeatureToggleService featureToggleService = getFeatureToggleService(context);
                boolean originalState = getFeatureStateFromProperties(context, featureName);

                if (originalState) {
                    featureToggleService.enable(featureName);
                } else {
                    featureToggleService.disable(featureName);
                }
            }
        });
    }

    private boolean getFeatureStateFromProperties(ExtensionContext context, String featureName) {
        Environment environment = SpringExtension.getApplicationContext(context).getEnvironment();
        return environment.getProperty("application.feature.toggles." + featureName, Boolean.class, Boolean.FALSE);
    }

    private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
    }
}
