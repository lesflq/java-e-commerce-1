package org.example.lab1.controllers.cats;

import org.example.lab1.service.implementation.CosmoCatServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatServiceImpl cosmoCatServiceImpl;

    public CosmoCatController(CosmoCatServiceImpl cosmoCatServiceImpl) {
        this.cosmoCatServiceImpl = cosmoCatServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<String>> getCosmoCats() {
        List<String> cosmoCats = cosmoCatServiceImpl.getCosmoCats();
        return ResponseEntity.ok(cosmoCats);
    }
}