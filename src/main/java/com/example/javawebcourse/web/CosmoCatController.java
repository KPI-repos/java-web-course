package com.example.javawebcourse.web;

import com.example.javawebcourse.dto.CosmoCatDto;
import com.example.javawebcourse.featuretoggle.FeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggles;
import com.example.javawebcourse.service.CosmoCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    @Autowired
    public CosmoCatController(CosmoCatService cosmoCatService) {
        this.cosmoCatService = cosmoCatService;
    }

    @GetMapping
    public List<CosmoCatDto> getAllCosmoCats() {
        return cosmoCatService.getCosmoCats();
    }
}


