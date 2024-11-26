package org.example.lab1.controllers;

import org.example.lab1.config.GreetingProperties;
import org.example.lab1.config.GreetingProperties.CatGreeting;
import org.example.lab1.exception.CatNotFoundException;
import org.example.lab1.featuretoggle.FeatureToggles;
import org.example.lab1.featuretoggle.annotation.FeatureToggle;
import org.example.lab1.featuretoggle.exception.FeatureNotAvailableException;
import org.example.lab1.featuretoggle.service.FeatureToggleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    private final GreetingProperties greetingProperties;

    public GreetingController(GreetingProperties greetingProperties) {
        this.greetingProperties = greetingProperties;
    }

    @GetMapping("/{name}")
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<String> getCustomerById(@PathVariable String name) {
        String greeting = ofNullable(greetingProperties.getGreetings()
                .get(name)).map(CatGreeting::getMessage).orElseThrow(() ->
                new CatNotFoundException(name));
        return ResponseEntity.ok(greeting);
    }
}