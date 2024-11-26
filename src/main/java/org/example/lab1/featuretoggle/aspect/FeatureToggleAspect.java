package org.example.lab1.featuretoggle.aspect;

import org.aspectj.lang.annotation.Before;
import org.example.lab1.featuretoggle.annotation.FeatureToggle;
import org.example.lab1.featuretoggle.exception.FeatureNotAvailableException;
import org.example.lab1.featuretoggle.service.FeatureToggleService;
import org.example.lab1.featuretoggle.FeatureToggles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggleAnnotation(FeatureToggle featureToggle) {
        FeatureToggles toggle = featureToggle.value();

        if (!featureToggleService.check(toggle.getFeatureName())) {
            log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
            throw new FeatureNotAvailableException(toggle.getFeatureName());
        }
    }
}