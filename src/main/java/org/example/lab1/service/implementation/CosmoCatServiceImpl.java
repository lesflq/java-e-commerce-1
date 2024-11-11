package org.example.lab1.service.implementation;

import org.example.lab1.featuretoggle.FeatureToggles;
import org.example.lab1.featuretoggle.annotation.FeatureToggle;
import org.example.lab1.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CosmoCatServiceImpl implements CosmoCatService {
    @Override
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public List<String> getCosmoCats() {
        return List.of("tisha", "charlik");
    }
}
