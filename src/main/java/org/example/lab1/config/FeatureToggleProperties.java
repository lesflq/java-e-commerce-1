package org.example.lab1.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "feature")
public class FeatureToggleProperties {
    Map<String, Boolean> toggles = new HashMap<>();
    public boolean check(String featureToggle){
        return toggles.getOrDefault(featureToggle, false);
    }
}