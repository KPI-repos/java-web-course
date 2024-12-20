package com.example.javawebcourse.featuretoggle.aspect;


import com.example.javawebcourse.featuretoggle.FeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggleService;
import com.example.javawebcourse.featuretoggle.FeatureToggles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.example.javawebcourse.featuretoggle.exception.FeatureNotAvailableException;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(com.example.javawebcourse.featuretoggle.FeatureToggle)")
    public void checkFeatureToggleAnnotation(JoinPoint joinPoint) throws Throwable {
        var methodSign = (MethodSignature) joinPoint.getSignature();
        var featureToggle = methodSign.getMethod().getAnnotation(FeatureToggle.class);
        checkToggle(featureToggle);
    }

    private void checkToggle(FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();

        if (!featureToggleService.check(toggle.getFeatureName())) {
            log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
            throw new FeatureNotAvailableException(toggle.getFeatureName());
        }
    }

}