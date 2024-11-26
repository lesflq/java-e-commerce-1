package org.example.lab1.service;

import org.example.lab1.config.FeatureToggleProperties;
import org.example.lab1.featuretoggle.aspect.FeatureToggleAspect;
import org.example.lab1.featuretoggle.exception.FeatureNotAvailableException;
import org.example.lab1.featuretoggle.service.FeatureToggleService;
import org.example.lab1.service.implementation.CosmoCatServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(SpringExtension.class)  // Завантажує контекст Spring для аспекту
@Import(CosmoCatServiceTest.Config.class)  // Імпортує конфігурацію тестів
public class CosmoCatServiceTest {

    @TestConfiguration
    @EnableAspectJAutoProxy
    static class Config {
        @Bean
        public FeatureToggleAspect featureToggleAspect(FeatureToggleService featureToggleService) {
            return new FeatureToggleAspect(featureToggleService);
        }

        @Bean
        public FeatureToggleService featureToggleService() {
            return new FeatureToggleService(new FeatureToggleProperties());
        }

        @Bean
        public CosmoCatServiceImpl cosmoCatService() {
            return new CosmoCatServiceImpl();
        }
    }

    @MockBean
    private FeatureToggleService featureToggleService;  // Mocks the FeatureToggleService at the Spring level

    @Autowired
    private CosmoCatService cosmoCatService;

    @Test
    void getCosmoCats_ShouldReturnList_WhenFeatureIsEnabled() {
        // Stubbing to make the "cosmoCats" feature enabled
        when(featureToggleService.check("cosmoCats")).thenReturn(true);

        // Execute the method
        List<String> result = cosmoCatService.getCosmoCats();

        // Assert the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(List.of("tisha", "charlik"), result);
    }

    @Test
    void getCosmoCats_ShouldThrowException_WhenFeatureIsDisabled() {
        when(featureToggleService.check("cosmoCats")).thenReturn(false);

        FeatureNotAvailableException exception = assertThrows(
                FeatureNotAvailableException.class,
                () -> cosmoCatService.getCosmoCats()
        );

        assertEquals("cosmoCats", exception.getMessage());
    }
}
